package org.practice.basicmangodb.service;

import lombok.val;
import org.practice.basicmangodb.comparators.*;
import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.exceptions.NoGamesFoundException;
import org.practice.basicmangodb.exceptions.NoUserFoundException;
import org.practice.basicmangodb.exceptions.UnableToAddGameException;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.repository.GameCollectionRepositoryI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameServiceI {

    private static final String PLATFORM = "platform";
    private static final String RATING = "rating";

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public GameServiceImpl(GameCollectionRepositoryI gameCollectionRepositoryI){
       this.gameCollectionRepositoryI = gameCollectionRepositoryI;
    }

    @Override
    public List<GameResponse> getAllGamesByUser(String user, String orderBy, String sortedBy){
        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()) {
            return gameCollectionRepositoryI.findAllByUser(user)
                    .get()
                    .stream()
                    .map(e -> this.mapToGameResponse(e, orderBy, sortedBy))
                    .toList();

        } else {
            throw new NoGamesFoundException("No games found matching criteria.");
        }
    }

    @Override
    public List<GameResponse> getUserGamesByPlatform(String user, Platforms platform) {
        isUsernamePresent(user);

        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user)
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
    public void addGamesToUserNewCollection(List<Game> newGames, String user) {
        GameDocument gameDocument = new GameDocument((ArrayList<Game>) newGames, user);

        gameCollectionRepositoryI.save(gameDocument);
    }

    @Override
    public void addAnNewGameFotAnExistingUser(List<Game> newGame, String user){
        isUsernamePresent(user);

        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()){
            GameDocument gameDocument = gameCollectionRepositoryI.findAllByUser(user).get().get(0);

            for(Game game : newGame){
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
    public List<GameResponse> getAllGamesIsPreOrder(Boolean isPreOrder, String user) {
        ArrayList<Game> gameList = new ArrayList<>();

        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()){
            List<Game> list = gameCollectionRepositoryI.findAllByUser(user)
                    .get()
                    .get(0)
                    .getGame()
                    .stream()
                    .filter(Game::getIsPreOrder)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("There are no games on pre order.");
        }
    }

    @Override
    public List<GameResponse> getAllGamesIsCompleted(Boolean isCompleted, String user) {
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user)
                    .get()
                    .get(0)
                    .getGame()
                    .stream()
                    .sorted(new ReleaseDateComparatorDsc())
                    .filter(e -> e.getIsCompleted() == isCompleted)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("There are no games are completed");
        }
    }

    @Override
    public List<GameResponse> getAllGamesByGenre(Genre genre, String user) {
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user)
                    .get()
                    .get(0)
                    .getGame()
                    .stream()
                    .sorted(new ReleaseDateComparatorAsc())
                    .filter(e -> e.getGenre() == genre)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("There are no games in selected genre.");
        }
    }

    @Override
    public void deleteGameFromUser(String user, String gameName){}

    @Override
    public List<GameResponse> getAllGamesNotReleased(String user){
        GameResponse gameResponse = null;

        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()){
            ArrayList<Game> listOfGamesForUser = gameCollectionRepositoryI.findAllByUser(user).get().get(0).getGame();

            var gamesToBeReleasedList = listOfGamesForUser
                    .stream()
                    .filter(e -> e.getReleaseDate().isAfter(LocalDate.now()))
                    .toArray(Game[]::new);

            gameResponse = new GameResponse(user, new ArrayList<>(List.of(gamesToBeReleasedList)));
        }

        return List.of(gameResponse);
    }

    @Override
    public List<GameResponse> getAllGamesOwned(boolean isOwned, String user){
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user)
                    .get()
                    .get(0)
                    .getGame()
                    .stream()
                    .sorted(new ReleaseDateComparatorAsc())
                    .filter(e -> e.getIsOwned() == isOwned)
                    .toList();

            return List.of(getGameResponse(user, list, gameList));
        } else {
            throw new NoGamesFoundException("There are no games completed");
        }
    }

    private GameResponse mapToGameResponse(GameDocument document, String orderBy, String sortedBy){
        if(orderBy.contentEquals("DESC") && sortedBy.contentEquals("RATING")) {
            List<Game> list = document.getGame()
                    .stream()
                    .sorted(new RatingComparatorDsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    //.sorted(new PlatformComparator())
                    .toList();

            return getGameResponse(document.getUser(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("ASC") && sortedBy.contentEquals("RATING")) {
            val list = document.getGame()
                    .stream()
                    .sorted(new RatingComparatorAsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    //.sorted(new PlatformComparator())
                    .toList();

            return getGameResponse(document.getUser(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("DESC") && sortedBy.contentEquals("PLATORM")){
            val list = document.getGame()
                    .stream()
                    //.sorted(new RatingComparatorDsc())
                    .sorted(new PlatformComparatorAsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser(), list, new ArrayList<>());
        } else if(orderBy.contentEquals("ASC") && sortedBy.contentEquals("PLATORM")){
            val list = document.getGame()
                    .stream()
                    //.sorted(new RatingComparatorAsc())
                    .sorted(new PlatformComparatorDsc())
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser(), list, new ArrayList<>());
        } else {
            val list = document
                    .getGame()
                    .stream()
                    .sorted(new ReleaseDateComparatorAsc())
                    .toList();

            return getGameResponse(document.getUser(), list, new ArrayList<>());
        }
    }

    private GameResponse getGameResponse(String user, List<Game> list, ArrayList<Game> gameList) {
        list.forEach(e ->this.convertToGame(gameList, e));

        return new GameResponse(user, gameList);
    }

    private void convertToGame(ArrayList<Game> gameList, Game game){
        gameList.add(game);
    }

    private Optional<ArrayList<GameDocument>> findGamesByTheUser(String user){
        return gameCollectionRepositoryI.findAllByUser(user);
    }

    private void isUsernamePresent(String userName) throws NoUserFoundException {
        if(!gameCollectionRepositoryI.existsByUser(userName)){
            throw new NoUserFoundException(String.format("%s is not registered", userName));
        }
    }

    private void isSameGameOnSamePlatform(ArrayList<Game> list, Game newGame, String user){
        if(list.stream().anyMatch(e -> e.getName().contentEquals(newGame.getName()) &&
                e.getPlatform().toString().contentEquals(newGame.getPlatform().name()))){
           throw new UnableToAddGameException(String.format("%s on %s already found for user %s.",
                   newGame.getName(),
                   newGame.getPlatform(),
                   user));
        };
    }

    private void processUpdateParameters(UpdateParameters updateParameters){
        if (gameCollectionRepositoryI.findById(updateParameters.id()).isPresent()) {
            GameDocument gameDocument = gameCollectionRepositoryI.findById(updateParameters.id()).get();

            Game game = gameDocument.getGame().stream()
                    .filter(e -> e.getName().contentEquals(updateParameters.gameToUpdate()))
                    .findFirst()
                    .orElseThrow();

            if(updateParameters.keyToUpdate().contentEquals(PLATFORM)) {
                game.setPlatform(Platforms.valueOf((String) updateParameters.newValue()));
            } else if (updateParameters.keyToUpdate().contentEquals(RATING)) {
                game.setRating((Double) updateParameters.newValue());
            } else if (updateParameters.keyToUpdate().contentEquals("releaseDate")) {
                game.setReleaseDate(LocalDate.parse((String) updateParameters.newValue()));
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
