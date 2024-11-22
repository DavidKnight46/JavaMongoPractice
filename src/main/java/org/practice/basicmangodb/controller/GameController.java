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

    private final GameServiceI gameService;

    public GameController(GameServiceImpl gameService){
        this.gameService = gameService;
    }

    @GetMapping("/findAnGameContainingForUser")
    @ResponseStatus(HttpStatus.OK)
    public List<GameDocumentDTO> findAnGameContainingForUser(@RequestParam String user,
                                             @RequestParam String gameString){
        return gameService.findAnGameContainingForUser(gameString,user);
    }

    @GetMapping("/getAnGame")
    @ResponseStatus(HttpStatus.OK)
    public GameDocumentDTO getAnSpecificGame(@RequestParam String user,
                                             @RequestParam String gameString){
        return gameService.getAnGame(gameString,user);
    }

    @GetMapping("/getAllGamesForUser")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponseDTO> getAllGamesByUser(@RequestParam String user,
                                                    @RequestParam(defaultValue = "ASC") String orderBy,
                                                    @RequestParam(defaultValue = "RATING") String sortedBy){
        return gameService.getAllGamesByUser(user, orderBy, sortedBy);
    }

    @GetMapping("/getAllGamesForUserByPlatform")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponseDTO> getUserGamesByPlatform(@RequestParam String user,
                                                        @RequestParam Platforms platform){
        return gameService.getUserGamesByPlatform(user, platform);
    }

    @PostMapping("/addNewGamesToNewUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGamesToUserNewCollection(@RequestBody UserCollection userCollection){
        gameService.addGamesToUserNewCollection(userCollection);
    }

    @PostMapping("/addAnNewGameForAnExistingUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAnNewGameForAnExistingUser(@RequestBody List<GameDocumentDTO> newGame,
                                              @RequestParam String user){
        gameService.addAnNewGameFotAnExistingUser(newGame, user);
    }

    @PutMapping("/updateGameInUserCollection")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGame(@RequestBody List<UpdateParameters> updateParameters,
                           @RequestParam String user){
        gameService.updateGame(user, updateParameters);
    }

    @GetMapping("/getAllGamesIsPreOrder")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesIsPreOrder(@RequestParam Boolean isPreOrder,
                                                 @RequestParam String user){
        return gameService.getAllGamesIsPreOrder(isPreOrder, user);
    }

    @GetMapping("/getAllGamesIsCompleted")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesIsCompleted(@RequestParam Boolean isCompleted,
                                                  @RequestParam String user){
        return gameService.getAllGamesIsCompleted(isCompleted, user);
    }

    @GetMapping("/getAllGamesByGenre")
    @ResponseStatus(HttpStatus.OK)
    public GameResponseDTO getAllGamesByGenre(@RequestParam Genre genre,
                                              @RequestParam String user){
        return gameService.getAllGamesByGenre(genre, user);
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
        return gameService.getAllGamesNotReleased(user);
    }

    @GetMapping("/getAllGamesUserOwns")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponseDTO> getAllGamesOwnedByUser(@RequestParam String user){
        return gameService.getAllGamesOwned(true, user);
    }
}