package org.practice.basicmangodb.comparators;

import org.practice.basicmangodb.models.game.Game;

import java.util.Comparator;

public class ReleaseDateComparatorAsc implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        if(o1.getReleaseDate().isAfter(o2.getReleaseDate())){
            return -1;
        } else {
            return 0;
        }
    }
}
