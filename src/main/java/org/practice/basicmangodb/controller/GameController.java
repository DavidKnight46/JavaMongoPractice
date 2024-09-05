package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.service.GameServiceI;
import org.practice.basicmangodb.service.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/gamecontroller")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final GameServiceI gameServiceI;

    public GameController(GameServiceImpl mangoDBI){
        this.gameServiceI = mangoDBI;
    }

    @GetMapping("/getAllGamesForUser")
    @ResponseStatus(HttpStatus.OK)
    private List<GameResponse> getAllGamesByUser(String user){
        return gameServiceI.getAllGamesByUser(user);
    }

    @GetMapping("/getAllGamesForUserByPlatform")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getUserGamesByPlatform(@RequestParam String user, @RequestParam Platforms platform){
        return gameServiceI.getUserGamesByPlatform(user, platform);
    }

    @PostMapping("/addNewGamesToNewUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGamesToUserNewCollection(@RequestBody List<Game> document, @RequestParam String user){
        gameServiceI.addGamesToUserNewCollection(document, user);
    }

    @PutMapping("/updateGameInUserCollection")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGame(@RequestBody List<UpdateParameters> updateParameters, @RequestParam String user){
        gameServiceI.updateGame(user, updateParameters);
    }

    @GetMapping("/getAllGamesIsPreOrder")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getAllGamesIsPreOrder(@RequestParam Boolean isPreOrder, @RequestParam String user){
        return gameServiceI.getAllGamesIsPreOrder(isPreOrder, user);
    }

    @GetMapping("/getAllGamesIsCompleted")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getAllGamesIsCompleted(@RequestParam Boolean isCompleted, @RequestParam String user){
        return gameServiceI.getAllGamesIsCompleted(isCompleted, user);
    }

    @GetMapping("/getAllGamesByGenre")
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getAllGamesByGenre(@RequestParam Genre genre, @RequestParam String user){
        return gameServiceI.getAllGamesByGenre(genre, user);
    }
}
