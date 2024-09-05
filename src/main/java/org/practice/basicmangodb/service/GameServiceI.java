package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;

import java.util.List;

public interface GameServiceI {

    List<GameResponse> getAllGamesByUser(String user);

    GameResponse getUserGamesByPlatform(String user, Platforms platform);

    void addGamesToUserNewCollection(List<Game> newGames, String user);

    void updateGame(String user, List<UpdateParameters> game);

    GameResponse getAllGamesIsPreOrder(Boolean isPreOrder, String user);

    GameResponse getAllGamesIsCompleted(Boolean isCompleted, String user);

    GameResponse getAllGamesByGenre(Genre genre, String user);
}
