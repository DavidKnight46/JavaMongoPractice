package org.practice.basicmangodb.service.game;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.UserCollection;
import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.dto.GameResponseDTO;
import org.practice.basicmangodb.models.game.UpdateParameters;

import java.util.List;

public interface GameServiceI {

    List<GameResponseDTO> getAllGamesByUser(String user, String orderBy, String sortedBy);

    List<GameResponseDTO> getUserGamesByPlatform(String user, Platforms platform);

    void addAnNewGameFotAnExistingUser(List<GameDocumentDTO> newGame, String user);

    void addGamesToUserNewCollection(UserCollection userCollection);

    void updateGame(String user, List<UpdateParameters> game);

    GameResponseDTO getAllGamesIsPreOrder(Boolean isPreOrder, String user);

    GameResponseDTO getAllGamesIsCompleted(Boolean isCompleted, String user);

    GameResponseDTO getAllGamesByGenre(Genre genre, String user);

    void deleteGameFromUser(String user, String gameName);

    GameResponseDTO getAllGamesNotReleased(String user);

    List<GameResponseDTO> getAllGamesOwned(boolean isOwned, String user);
}
