package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.exceptions.NoGamesFoundException;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.repository.GameCollectionRepositoryI;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GameServiceImpl implements GameServiceI {

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public GameServiceImpl(GameCollectionRepositoryI gameCollectionRepositoryI){
       this.gameCollectionRepositoryI = gameCollectionRepositoryI;
    }

    @Override
    public List<GameResponse> getFromMangoDB(String user, Platforms platform) {
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
    public void addToDB(GameDocument document) {
        gameCollectionRepositoryI.insert(document);
    }

    @Override
    public void updateGame(UpdateParameters updateParameters){
            if (gameCollectionRepositoryI.findById(updateParameters.id()).isPresent()) {
                GameDocument gameDocument = gameCollectionRepositoryI.findById(updateParameters.id()).get();

                Game game = Arrays
                        .stream(gameDocument.getGame())
                        .filter(e -> e.getName().contentEquals(updateParameters.gameToUpdate()))
                        .findFirst()
                        .orElseThrow();

                game.setPlatform((String) updateParameters.newValue());

                gameCollectionRepositoryI.save(gameDocument);
            }
    }

    private GameResponse mapToGameResponse(GameDocument document){
        return new GameResponse(document.getUser(), document.getGame());
    }
}
