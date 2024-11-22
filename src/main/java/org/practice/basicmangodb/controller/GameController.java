package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.UserCollection;
import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.dto.GameResponseDTO;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.service.game.GameServiceI;
import org.practice.basicmangodb.service.game.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamecontroller")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final GameServiceI gameServiceI;

    public GameController(GameServiceImpl mangoDBI){
        this.gameServiceI = mangoDBI;
    }

    @CrossOrigin
    @GetMapping("/getAllGamesForUser")
    @ResponseStatus(HttpStatus.OK)
    private List<GameResponseDTO> getAllGamesByUser(@RequestParam String user,
                                                    @RequestParam(defaultValue = "ASC") String orderBy,
                                                    @RequestParam(defaultValue = "RATING") String sortedBy){
        return gameServiceI.getAllGamesByUser(user, orderBy, sortedBy);
    }

    @GetMapping("/getAllGamesForUserByPlatform")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponseDTO> getUserGamesByPlatform(@RequestParam String user,
                                                        @RequestParam Platforms platform){
        return gameServiceI.getUserGamesByPlatform(user, platform);
    }

    @PostMapping("/addNewGamesToNewUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGamesToUserNewCollection(@RequestBody UserCollection userCollection){
        gameServiceI.addGamesToUserNewCollection(userCollection);
    }

    @PostMapping("/addAnNewGameForAnExistingUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAnNewGameForAnExistingUser(@RequestBody List<GameDocumentDTO> newGame,
                                              @RequestParam String user){
        gameServiceI.addAnNewGameFotAnExistingUser(newGame, user);
    }

    @PutMapping("/updateGameInUserCollection")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGame(@RequestBody List<UpdateParameters> updateParameters,
                           @RequestParam String user){
        gameServiceI.updateGame(user, updateParameters);
    }

    @GetMapping("/getAllGamesIsPreOrder")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesIsPreOrder(@RequestParam Boolean isPreOrder,
                                                 @RequestParam String user){
        return gameServiceI.getAllGamesIsPreOrder(isPreOrder, user);
    }

    @GetMapping("/getAllGamesIsCompleted")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesIsCompleted(@RequestParam Boolean isCompleted,
                                                  @RequestParam String user){
        return gameServiceI.getAllGamesIsCompleted(isCompleted, user);
    }

    @GetMapping("/getAllGamesByGenre")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesByGenre(@RequestParam Genre genre,
                                              @RequestParam String user){
        return gameServiceI.getAllGamesByGenre(genre, user);
    }

    @DeleteMapping("/deleteGameFromUser")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGameFromUser(@RequestParam String user,
                                   @RequestParam String gameName){
        //TODO: Not yet implemented
    }

    @GetMapping("/getAllGamesNotReleased")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesNotReleased(@RequestParam String user){
        return gameServiceI.getAllGamesNotReleased(user);
    }

    @GetMapping("/getAllGamesUserOwns")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponseDTO> getAllGamesOwnedByUser(@RequestParam String user){
        return gameServiceI.getAllGamesOwned(true, user);
    }
}