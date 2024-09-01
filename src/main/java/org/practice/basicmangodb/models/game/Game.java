package org.practice.basicmangodb.models.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Game {

    private String name;
    private String platform;
    private Double rating;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    public Game(){}

    public Game(String name, String platform, Double rating, LocalDate releaseDate){
        this.name = name;
        this.platform = platform;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

}
