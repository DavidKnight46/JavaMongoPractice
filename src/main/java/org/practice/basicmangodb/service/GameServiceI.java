package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;

import java.util.List;

public interface GameServiceI {

    List<GameResponse> getAllGamesByUser(String user, String orderBy, String sortedBy);

    List<GameResponse> getUserGamesByPlatform(String user, Platforms platform);

    void addAnNewGameFotAnExistingUser(List<Game> newGame, String user);

    void addGamesToUserNewCollection(List<Game> newGames, String user);

    void updateGame(String user, List<UpdateParameters> game);

    List<GameResponse> getAllGamesIsPreOrder(Boolean isPreOrder, String user);

    List<GameResponse> getAllGamesIsCompleted(Boolean isCompleted, String user);

    List<GameResponse> getAllGamesByGenre(Genre genre, String user);

    void deleteGameFromUser(String user, String gameName);

    List<GameResponse> getAllGamesNotReleased(String user);

    List<GameResponse> getAllGamesOwned(boolean isOwned, String user);
}
