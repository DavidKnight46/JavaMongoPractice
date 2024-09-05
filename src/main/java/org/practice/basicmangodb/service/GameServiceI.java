package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;

import java.util.List;

public interface GameServiceI {

    List<GameResponse> getUserGamesByPlatform(String user, Platforms platform);

    void addGameToExistingUserCollection(GameDocument document);

    void updateGame(List<UpdateParameters> updateParameters);

    void addGamesToUserNewCollection(String user, List<Game> game);
}
