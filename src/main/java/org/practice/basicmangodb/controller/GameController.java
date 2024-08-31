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
    public List<GameResponse> getFromDB(@RequestParam String user,
                                        @RequestParam Platforms platform){
        return gameServiceI.getFromMangoDB(user, platform);
    }

    @PostMapping("/addGameToRepository")
    @ResponseStatus(HttpStatus.CREATED)
    public void postToMongo(@RequestBody GameDocument document){
        gameServiceI.addToDB(document);
    }

    @PutMapping("/updateGame")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGame(@RequestBody UpdateParameters updateParameters){
        gameServiceI.updateGame(updateParameters);
    }

    @PostMapping("/addGameToCollection")
    public void addGameToCollection(@RequestParam String user, @RequestBody Game newGame){
        gameServiceI.addGameToUserCollection(user, newGame);
    }
}
