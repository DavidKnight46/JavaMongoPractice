package org.practice.basicmangodb.models.game;

import lombok.Data;

@Data
public class Game {

    private String name;
    private String platform;

    public Game(){}

    public Game(String name, String platform){
        this.name = name;
        this.platform = platform;
    }

}
