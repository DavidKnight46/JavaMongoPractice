package org.practice.basicmangodb.models.game;

import java.util.ArrayList;

public record GameResponse(String user, ArrayList<Game> games) {
}
