package org.practice.basicmangodb.comparators;

import org.practice.basicmangodb.models.game.Game;

import java.util.Comparator;

public class PlatformComparatorAsc implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        if(o1.getPlatform().ordinal() < o2.getPlatform().ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }
}
