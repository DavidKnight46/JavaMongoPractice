package org.practice.basicmangodb.service;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameServiceI {

    List<GameResponse> getAllGamesByUser(String user, String orderBy);

    GameResponse getUserGamesByPlatform(String user, Platforms platform);

    void addAnNewGameFotAnExistingUser(Game newGame, String user);

    void addGamesToUserNewCollection(List<Game> newGames, String user);

    void updateGame(String user, List<UpdateParameters> game);

    GameResponse getAllGamesIsPreOrder(Boolean isPreOrder, String user);

    GameResponse getAllGamesIsCompleted(Boolean isCompleted, String user);

    GameResponse getAllGamesByGenre(Genre genre, String user);

    void deleteGameFromUser(String user, String gameName);
}
