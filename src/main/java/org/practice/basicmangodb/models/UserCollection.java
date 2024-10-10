package org.practice.basicmangodb.models;

import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.user.User;

import java.util.List;

public record UserCollection(List<Game> newGame, User user) {
}
