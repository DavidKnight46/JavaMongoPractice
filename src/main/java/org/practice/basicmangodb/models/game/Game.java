package org.practice.basicmangodb.models.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.practice.basicmangodb.enums.Genre;
import org.practice.basicmangodb.enums.Platforms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class Game {

    private String name;
    private Platforms platform;
    private Double rating;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseDate;
    private Boolean isPreOrder;
    private Boolean isInterested;
    private Genre genre;
    private Boolean isCompleted;
    private Boolean isOwned;
    private ArrayList<DLC> dlcs;
    public Game(){}

    public Game(String name, Platforms platform, Double rating, LocalDateTime releaseDate, Boolean isPreOrder, Boolean isInterested, Genre genre, Boolean isCompleted, Boolean isOwned, ArrayList<DLC> dlcs){
        this.name = name;
        this.platform = platform;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.isPreOrder = isPreOrder;
        this.isInterested = isInterested;
        this.genre = genre;
        this.isCompleted = isCompleted;
        this.isOwned = isOwned;
        this.dlcs = dlcs;
    }

}
