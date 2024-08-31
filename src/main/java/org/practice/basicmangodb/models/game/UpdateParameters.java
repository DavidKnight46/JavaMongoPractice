package org.practice.basicmangodb.models.game;

public record UpdateParameters(String id, String gameToUpdate, String keyToUpdate, Object newValue) {
}