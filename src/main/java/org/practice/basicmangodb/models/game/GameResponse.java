package org.practice.basicmangodb.models.game;

import org.practice.basicmangodb.models.user.User;

import java.util.ArrayList;

public record GameResponse(String alias, ArrayList<Game> games, boolean isAdmin) {


}
