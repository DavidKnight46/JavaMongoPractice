package org.practice.basicmangodb.repository;

import org.practice.basicmangodb.enums.Platforms;
import org.practice.basicmangodb.models.game.GameDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameCollectionRepositoryI extends MongoRepository<GameDocument, String> {

    Optional<ArrayList<GameDocument>> findAllByUser(String user);

    @Query("{$and:[{ \"game.platform\": ?0 }, {user: ?1}]}")
    Optional<List<GameDocument>> findByPlatformAndUser(Platforms platform, String user);

    boolean existsByUser(String username);

    @Query("{$and:[{ \"game.isPreOrder\": ?0 }, {user: ?1}]}")
    List<GameDocument> findByIsPreOrder(Boolean isPreOrder, String user);
}
