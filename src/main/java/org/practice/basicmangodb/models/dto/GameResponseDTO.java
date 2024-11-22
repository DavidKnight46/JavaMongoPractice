package org.practice.basicmangodb.models.dto;

import java.util.List;

public record GameResponseDTO(String alias, List<GameDocumentDTO> games, boolean isAdmin) {


}
