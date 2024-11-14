package org.practice.basicmangodb.service.dlcservice;

import lombok.val;
import org.practice.basicmangodb.models.dto.DlcDTO;
import org.practice.basicmangodb.models.game.DLC;
import org.practice.basicmangodb.models.game.Game;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.repository.GameCollectionRepositoryI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DLCServiceImpl implements DLCServiceI{

    private final GameCollectionRepositoryI gameCollectionRepositoryI;

    public DLCServiceImpl(GameCollectionRepositoryI repositoryI){
        this.gameCollectionRepositoryI = repositoryI;
    }

    @Override
    public void addAnDLC(DlcDTO dlc, String user) {

        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user).isPresent()){
            List<GameDocument> gameDocuments = gameCollectionRepositoryI.findGameDocumentByUserUsername(user).get();

            for(GameDocument doc : gameDocuments){
                ArrayList<Game> games = doc.getGame();

                var game = games.stream()
                        .filter(e -> e.getName().contentEquals(dlc.gameName()))
                        .findFirst();

                if(game.isPresent()) {
                    game.get()
                            .getDlcs()
                            .add(new DLC(dlc.dlcName(), dlc.releaseDate(), dlc.rating()));

                    gameCollectionRepositoryI.save(doc);
                }
            }
        }
    }

    @Override
    public void addAllDLCs(List<DlcDTO> dlcs, String user) {

    }
}
