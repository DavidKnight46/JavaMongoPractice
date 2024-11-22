package org.practice.basicmangodb.comparators;

import org.practice.basicmangodb.models.dto.GameDocumentDTO;

import java.util.Comparator;

public class ReleaseDateComparatorAsc implements Comparator<GameDocumentDTO> {
    @Override
    public int compare(GameDocumentDTO o1, GameDocumentDTO o2) {
        if(o1.getReleaseDate().isAfter(o2.getReleaseDate())){
            return -1;
        } else {
            return 0;
        }
    }
}
