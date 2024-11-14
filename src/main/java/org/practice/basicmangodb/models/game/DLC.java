package org.practice.basicmangodb.models.game;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DLC {

    private String dlcName;
    private LocalDate releaseDate;
    private float rating;

    public DLC(){}

    public DLC(String dlcName, LocalDate releaseDate, float rating){
        this.dlcName = dlcName;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
}
