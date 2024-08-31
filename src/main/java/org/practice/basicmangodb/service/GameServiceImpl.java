package org.practice.basicmangodb.service;

import lombok.val;
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
    public void updateGame(UpdateParameters updateParameters){
            if (gameCollectionRepositoryI.findById(updateParameters.id()).isPresent()) {
                GameDocument gameDocument = gameCollectionRepositoryI.findById(updateParameters.id()).get();

                Game game = gameDocument.getGame().stream()
                        .filter(e -> e.getName().contentEquals(updateParameters.gameToUpdate()))
                        .findFirst()
                        .orElseThrow();

                if(updateParameters.keyToUpdate().contentEquals(PLATFORM)) {
                    game.setPlatform((String) updateParameters.newValue());
                } else if (updateParameters.keyToUpdate().contentEquals(RATING)) {
                    game.setRating((Double) updateParameters.newValue());
                }

                gameCollectionRepositoryI.save(gameDocument);
            }
    }

    @Override
    public void addGameToUserCollection(String user, Game newGame) {
        isUsernamePresent(user);

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
                e.getPlatform().contentEquals(newGame.getPlatform()))){
           throw new UnableToAddGameException(String.format("%s on %s already found for user %s.",
                   newGame.getName(),
                   newGame.getPlatform(),
                   user));
        };
    }

    private GameResponse mapToGameResponse(GameDocument document){
        return new GameResponse(document.getUser(), document.getGame());
    }
}
