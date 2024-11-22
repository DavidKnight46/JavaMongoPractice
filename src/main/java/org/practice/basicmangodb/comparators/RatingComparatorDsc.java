package org.practice.basicmangodb.comparators;

import org.practice.basicmangodb.models.dto.GameDocumentDTO;

import java.util.Comparator;

public class RatingComparatorDsc implements Comparator<GameDocumentDTO> {

    @Override
    public int compare(GameDocumentDTO o1, GameDocumentDTO o2) {
        if(o1.getRating() < o2.getRating()) {
            return -1;
        } else {
            return 0;
        }
    }
}
