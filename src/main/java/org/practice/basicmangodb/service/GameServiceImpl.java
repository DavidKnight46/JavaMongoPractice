package org.practice.basicmangodb.service;

import lombok.val;
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
import java.util.*;

@Service
public class GameServiceImpl implements GameServiceI {

    private static final String PLATFORM = "platform";
    private static final String RATING = "rating";

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public GameServiceImpl(GameCollectionRepositoryI gameCollectionRepositoryI){
       this.gameCollectionRepositoryI = gameCollectionRepositoryI;
    }

    @Override
    public List<GameResponse> getAllGamesByUser(String user){
        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()) {
            return gameCollectionRepositoryI.findAllByUser(user).get().stream().map(this::mapToGameResponse).toList();
        } else {
            throw new NoGamesFoundException("");
        }
    }

    @Override
    public GameResponse getUserGamesByPlatform(String user, Platforms platform) {
        isUsernamePresent(user);

        GameResponse response = new GameResponse(user, null);
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user).get().get(0).getGame().stream().filter(e -> e.getPlatform() == platform).toList();
            list.forEach(e ->this.convertToGame(gameList, response, e));

            return new GameResponse(user,gameList);
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
    public void updateGame(String user, List<UpdateParameters> games) {
        this.isUsernamePresent(user);

        for(UpdateParameters update : games) {
            this.processUpdateParameters(update);
        }
    }

    @Override
    public GameResponse getAllGamesIsPreOrder(Boolean isPreOrder, String user) {
        GameResponse response = new GameResponse(user, null);
        ArrayList<Game> gameList = new ArrayList<>();

        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()){
            List<Game> list = gameCollectionRepositoryI.findAllByUser(user).get().get(0).getGame().stream().filter(Game::getIsPreOrder).toList();
            list.forEach(e ->this.convertToGame(gameList, response, e));

            return new GameResponse(user, gameList);
        } else {
            throw new NoGamesFoundException("");
        }
    }

    @Override
    public GameResponse getAllGamesIsCompleted(Boolean isCompleted, String user) {
        GameResponse response = new GameResponse(user, null);
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user).get().get(0).getGame().stream().filter(Game::getIsCompleted).toList();
            list.forEach(e ->this.convertToGame(gameList, response, e));

            return new GameResponse(user, gameList);
        } else {
            throw new NoGamesFoundException("");
        }
    }

    @Override
    public GameResponse getAllGamesByGenre(Genre genre, String user) {
        GameResponse response = new GameResponse(user, null);
        ArrayList<Game> gameList = new ArrayList<>();

        if(findGamesByTheUser(user).isPresent()){
            List<Game> list = findGamesByTheUser(user).get().get(0).getGame().stream().filter(e -> e.getGenre() == genre).toList();
            list.forEach(e ->this.convertToGame(gameList, response, e));

            return new GameResponse(user, gameList);
        } else {
            throw new NoGamesFoundException("");
        }
    }

    //
    private void convertToGame(ArrayList<Game> gameList, GameResponse response, Game game){
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

    private void isSameGameOnSamePlatform(List<Game> list, Game newGame, String user){
        if(list.stream().anyMatch(e -> e.getName().contentEquals(newGame.getName()) &&
                e.getPlatform().toString().contentEquals(newGame.getPlatform().name()))){
           throw new UnableToAddGameException(String.format("%s on %s already found for user %s.",
                   newGame.getName(),
                   newGame.getPlatform(),
                   user));
        };
    }

    private GameResponse mapToGameResponse(GameDocument document){
        return new GameResponse(document.getUser(), document.getGame());
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
