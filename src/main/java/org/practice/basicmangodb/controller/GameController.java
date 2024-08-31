package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.models.game.GameResponse;
import org.practice.basicmangodb.models.game.UpdateParameters;
import org.practice.basicmangodb.service.GameServiceI;
import org.practice.basicmangodb.service.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/gamecontroller")
public class GameController {

    private final GameServiceI gameServiceI;

    public GameController(GameServiceImpl mangoDBI){
        this.gameServiceI = mangoDBI;
    }

    @GetMapping("/getGameByUserAndPlatform")
    @ResponseStatus(HttpStatus.OK)
    public List<GameResponse> getUserGamesByPlatform(@RequestParam String user,
                                                     @RequestParam Platforms platform){
        return gameServiceI.getUserGamesByPlatform(user, platform);
    }

    @PostMapping("/addGamesAndCreateUserCollection")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGamesAndCreateUserCollection(@RequestBody GameDocument document){
        gameServiceI.addGamesAndCreateUserCollection(document);
    }

    @PutMapping("/updateGame")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGame(@RequestBody UpdateParameters updateParameters){
        gameServiceI.updateGame(updateParameters);
    }

    @PostMapping("/addGameToUserCollection")
    public void addGameToUserCollection(@RequestParam String user,
                                        @RequestBody Game newGame){
        gameServiceI.addGameToUserCollection(user, newGame);
    }
}
