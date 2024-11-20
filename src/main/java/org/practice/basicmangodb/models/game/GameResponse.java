package org.practice.basicmangodb.models.game;

import java.util.ArrayList;
import java.util.List;

public record GameResponse(String alias, List<Game> games, boolean isAdmin) {


}
