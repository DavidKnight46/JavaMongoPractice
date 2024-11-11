package org.practice.basicmangodb.models.game;

import java.util.ArrayList;

public record GameResponse(String alias, ArrayList<Game> games, boolean isAdmin) {


}
