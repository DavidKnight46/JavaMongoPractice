package org.practice.basicmangodb.service;

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
import java.util.List;

@Service
public class GameServiceImpl implements GameServiceI {

    private static final String PLATFORM = "platform";
    private static final String RATING = "rating";

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public GameServiceImpl(GameCollectionRepositoryI gameCollectionRepositoryI){
       this.gameCollectionRepositoryI = gameCollectionRepositoryI;
    }

    @Override
    public List<GameResponse> getUserGamesByPlatform(String user, Platforms platform) {
        isUsernamePresent(user);

        //val list = gameCollectionRepositoryI.findByPlatformAndUser(platform, user).get();
        //val gameStream = list.get(0).getGame().stream().filter(Game::getIsPreOrder).toList();

        if(gameCollectionRepositoryI.findByPlatformAndUser(platform, user).orElseThrow().isEmpty()){
            throw new NoGamesFoundException(String.format("%s has no games found.",user));
        } else {
            return gameCollectionRepositoryI.findByPlatformAndUser(platform, user)
                    .orElseThrow()
                    .stream()
                    .map(this::mapToGameResponse)
                    .toList();
        }
    }

    @Override
    public void addGamesAndCreateUserCollection(GameDocument document) {
        gameCollectionRepositoryI.insert(document);
    }

    @Override
    public void updateGame(List<UpdateParameters> updateParameters){
        updateParameters.forEach(this::processUpdateParameters);
    }

    @Override
    public void addGameToUserCollection(String user, List<Game> newGame) {
        isUsernamePresent(user);
        newGame.forEach(e -> processAddGameToUserCollection(e, user));
    }

    private void processAddGameToUserCollection(Game newGame, String user){
        if(gameCollectionRepositoryI.findAllByUser(user).isPresent()){
            List<GameDocument> usersExistingGames = gameCollectionRepositoryI.findAllByUser(user).get();
            GameDocument userDocument = usersExistingGames.stream().findFirst().orElseThrow();

            isSameGameOnSamePlatform(userDocument.getGame(), newGame, user);
            userDocument.getGame().add(newGame);
            userDocument.setGame(userDocument.getGame());

            gameCollectionRepositoryI.save(userDocument);
        } else {
            throw new UnableToAddGameException(String.format("%s not present",
                    newGame.getName()));
        }
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
