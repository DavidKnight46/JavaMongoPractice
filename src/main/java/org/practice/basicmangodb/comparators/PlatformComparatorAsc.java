package org.practice.basicmangodb.comparators;

import org.practice.basicmangodb.models.dto.GameDocumentDTO;

import java.util.Comparator;

public class PlatformComparatorAsc implements Comparator<GameDocumentDTO> {
    @Override
    public int compare(GameDocumentDTO o1, GameDocumentDTO o2) {
        if(o1.getPlatform().ordinal() < o2.getPlatform().ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }
}
