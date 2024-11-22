package org.practice.basicmangodb.service.game;

import lombok.val;
import org.practice.basicmangodb.comparators.*;
import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.exceptions.NoGamesFoundException;
import org.practice.basicmangodb.exceptions.NoUserFoundException;
import org.practice.basicmangodb.exceptions.UnableToAddGameException;
import org.practice.basicmangodb.models.UserCollection;
import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.dto.GameResponseDTO;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.models.user.User;
import org.practice.basicmangodb.repository.GameCollectionRepositoryI;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GameServiceImpl implements GameServiceI {

    private static final String PLATFORM = "platform";
    private static final String RATING = "rating";

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public GameServiceImpl(GameCollectionRepositoryI gameCollectionRepositoryI){
       this.gameCollectionRepositoryI = gameCollectionRepositoryI;
    }

    @Override
    public GameDocumentDTO getAnGame(String gameTitle, String user) {
        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user)
                .isPresent())
        {
            return getGameStream(user)
                    .filter(e -> e.getName().contains(gameTitle))
                    .findFirst()
                    .get();
        } else {
            throw new NoGamesFoundException("There are no games containing title: " + gameTitle);
        }
    }

    @Override
    public List<GameResponseDTO> getAllGamesByUser(String user, String orderBy, String sortedBy){
        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user).isPresent()) {
            return gameCollectionRepositoryI.findGameDocumentByUserUsername(user)
                    .get()
                    .stream()
                    .map(e -> this.mapToGameResponse(e, orderBy, sortedBy))
                    .toList();

        } else {
            throw new NoGamesFoundException("No games found matching criteria.");
        }
    }

    @Override
    public List<GameResponseDTO> getUserGamesByPlatform(String user, Platforms platform) {
        isUsernamePresent(user);

        ArrayList<GameDocumentDTO> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<GameDocumentDTO> list = findGamesByTheUser(user)
                    .get()
                    .get(0)
                    .getGame()
                    .stream()
                    .filter(e -> e.getPlatform() == platform)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("");
        }
    }

    @Override
    public void addGamesToUserNewCollection(UserCollection userCollection) {
        GameDocument gameDocument = new GameDocument((ArrayList<GameDocumentDTO>) userCollection.newGame(), userCollection.user());

        gameCollectionRepositoryI.save(gameDocument);
    }

    @Override
    public void addAnNewGameFotAnExistingUser(List<GameDocumentDTO> newGame, String user){
        isUsernamePresent(user);

        if(gameCollectionRepositoryI.findAllByUser_Alias(user).isPresent()){
            GameDocument gameDocument = gameCollectionRepositoryI.findAllByUser_Alias(user).get().get(0);

            for(GameDocumentDTO game : newGame){
                if(gameDocument.getGame().contains(game)){
                    throw new UnableToAddGameException(String.format("%s already added on platform %s", game.getName(), game.getPlatform()));
                } else {
                    gameDocument.getGame().add(game);
                    gameCollectionRepositoryI.save(gameDocument);
                }
            }
        }
    }

    @Override
    public void updateGame(String user, List<UpdateParameters> games) {
        this.isUsernamePresent(user);

        for(UpdateParameters update : games) {
            this.processUpdateParameters(update);
        }
    }

    @Override
    public GameResponseDTO getAllGamesIsPreOrder(Boolean isPreOrder, String user) {
        if(gameCollectionRepositoryI.findAllByUser_Alias(user).isPresent()){
            List<GameDocumentDTO> list = getGameStream(user)
                    .filter(GameDocumentDTO::getIsPreOrder)
                    .toList();

            return new GameResponseDTO(user, list, false);
        } else {
            throw new NoGamesFoundException("There are no games on pre order.");
        }
    }

    @Override
    public GameResponseDTO getAllGamesIsCompleted(Boolean isCompleted, String user) {
        if(findGamesByTheUser(user).isPresent()){
            List<GameDocumentDTO> list = getGameStream(user)
                    .filter(e -> e.getIsCompleted() == isCompleted)
                    .toList();

            return new GameResponseDTO(user, list, false);
        } else {
            throw new NoGamesFoundException("There are no games are completed");
        }
    }

    @Override
    public GameResponseDTO getAllGamesByGenre(Genre genre, String user) {
        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user)
                .isPresent()){

            List<GameDocumentDTO> listOfGamesByGenre = getGameStream(user)
                    .filter(e -> e.getGenre() == genre)
                    .toList();

            return new GameResponseDTO(user, listOfGamesByGenre, false);
        } else {
            throw new NoGamesFoundException("There are no games in selected genre.");
        }
    }

    @Override
    public void deleteGameFromUser(String user, String gameName){}

    @Override
    public GameResponseDTO getAllGamesNotReleased(String user){
        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user)
                .isPresent()){

            List<GameDocumentDTO> listOfGamesByGenre = getGameStream(user)
                    .filter(e -> !e.getIsOwned())
                    .toList();

            return new GameResponseDTO(user, listOfGamesByGenre, false);
        } else {
            throw new NoGamesFoundException("There are no games in selected genre.");
        }
    }

    @Override
    public List<GameResponseDTO> getAllGamesOwned(boolean isOwned, String user){
        ArrayList<GameDocumentDTO> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<GameDocumentDTO> list = getGameStream(user)
                    .sorted(new ReleaseDateComparatorAsc())
                    .filter(e -> e.getIsOwned() == isOwned)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("There are no games completed");
        }
    }

    private Stream<GameDocumentDTO> getGameStream(String user) {
        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user).isPresent()) {
            return gameCollectionRepositoryI
                    .findGameDocumentByUserUsername(user)
                    .get()
                    .stream()
                    .toList()
                    .get(0)
                    .getGame()
                    .stream();
        } else {
            throw new NoGamesFoundException("No Games found.");
        }
    }

    private GameResponseDTO mapToGameResponse(GameDocument document, String orderBy, String sortedBy){
        if(orderBy.contentEquals("DESC") && sortedBy.contentEquals("RATING")) {
            List<GameDocumentDTO> list = document.getGame()
                    .stream()
                    .sorted(new RatingComparatorDsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser().getAlias(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("ASC") && sortedBy.contentEquals("RATING")) {
            val list = document.getGame()
                    .stream()
                    .sorted(new RatingComparatorAsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser().getAlias(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("DESC") && sortedBy.contentEquals("PLATORM")){
            val list = document.getGame()
                    .stream()
                    .sorted(new PlatformComparatorAsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser().getAlias(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("ASC") && sortedBy.contentEquals("PLATORM")){
            val list = document.getGame()
                    .stream()
                    .sorted(new PlatformComparatorDsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser().getAlias(), list, new ArrayList<>());
        } else {
            val list = document
                    .getGame()
                    .stream()
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser().getAlias(), list, new ArrayList<>());
        }
    }

    private GameResponseDTO getGameResponse(String user, List<GameDocumentDTO> list, ArrayList<GameDocumentDTO> gameList) {
        list.forEach(e ->this.convertToGame(gameList, e));

        User activeUser = gameCollectionRepositoryI
                .findAll()
                .stream()
                .filter(e -> e.getUser().getAlias().contentEquals(user))
                .findFirst()
                .get()
                .getUser();

        return new GameResponseDTO(user, gameList, activeUser.isAdmin());
    }

    private void convertToGame(ArrayList<GameDocumentDTO> gameList, GameDocumentDTO game){
        gameList.add(game);
    }

    private Optional<ArrayList<GameDocument>> findGamesByTheUser(String user){
        return gameCollectionRepositoryI.findAllByUser_Alias(user);
    }

    private void isUsernamePresent(String userName) throws NoUserFoundException {
        Optional<GameDocument> b = gameCollectionRepositoryI.existsByUser_Alias(userName);

        if(b.isEmpty()){
            throw new NoUserFoundException(String.format("%s is not registered", userName));
        }
    }

    private void processUpdateParameters(UpdateParameters updateParameters){
        if (gameCollectionRepositoryI.findById(updateParameters.id()).isPresent()) {
            GameDocument gameDocument = gameCollectionRepositoryI.findById(updateParameters.id()).get();

            GameDocumentDTO game = gameDocument.getGame().stream()
                    .filter(e -> e.getName().contentEquals(updateParameters.gameToUpdate()))
                    .findFirst()
                    .orElseThrow();

            if(updateParameters.keyToUpdate().contentEquals(PLATFORM)) {
                game.setPlatform(Platforms.valueOf((String) updateParameters.newValue()));
            } else if (updateParameters.keyToUpdate().contentEquals(RATING)) {
                game.setRating((Double) updateParameters.newValue());
            } else if (updateParameters.keyToUpdate().contentEquals("releaseDate")) {
                game.setReleaseDate(LocalDateTime.parse((String) updateParameters.newValue()));
            } else if (updateParameters.keyToUpdate().contentEquals("isPreOrder")){
                game.setIsPreOrder((Boolean) updateParameters.newValue());
            } else if(updateParameters.keyToUpdate().contentEquals("isInterested")) {
                game.setIsInterested((Boolean) updateParameters.newValue());
            } else if(updateParameters.keyToUpdate().contentEquals("genre")){
                game.setGenre(Genre.valueOf((String) updateParameters.newValue()));
            }

            gameCollectionRepositoryI.save(gameDocument);
        }
    }
}
