package org.practice.basicmangodb.models.game;

import lombok.Data;

@Data
public class Game {

    private String name;
    private String platform;
    private Double rating;

    public Game(){}

    public Game(String name, String platform, Double rating){
        this.name = name;
        this.platform = platform;
        this.rating = rating;
    }

}
