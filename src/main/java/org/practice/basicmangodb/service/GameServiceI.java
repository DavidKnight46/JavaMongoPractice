package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;

import java.util.List;

public interface GameServiceI {

    List<GameResponse> getFromMangoDB(String user, Platforms platform);

    void addToDB(GameDocument document);

    void updateGame(UpdateParameters updateParameters);

    void addGameToUserCollection(String user, Game game);
}
