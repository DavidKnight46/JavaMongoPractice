package org.practice.basicmangodb.models.game;

import lombok.Data;
import org.bson.types.ObjectId;
import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "gamesCollection")
@Data
public class GameDocument {

    @Id
    private ObjectId _id;
    private ArrayList<GameDocumentDTO> game;
    private User user;

    public GameDocument(){}

    public GameDocument(ArrayList<GameDocumentDTO> game, User user){
        this.game = game;
        this.user =  user;
    }
}
