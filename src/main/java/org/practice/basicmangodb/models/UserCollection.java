package org.practice.basicmangodb.models;

import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.user.User;

import java.util.List;

public record UserCollection(List<GameDocumentDTO> newGame, User user) {
}
