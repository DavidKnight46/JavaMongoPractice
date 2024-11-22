package org.practice.basicmangodb.service.dlcservice;

import org.practice.basicmangodb.exceptions.NoDLCFoundException;
import org.practice.basicmangodb.exceptions.NoGamesFoundException;
import org.practice.basicmangodb.models.dto.DlcDTO;
import org.practice.basicmangodb.models.game.dlc.DLC;
import org.practice.basicmangodb.models.dto.GameDocumentDTO;
import org.practice.basicmangodb.models.game.GameDocument;
import org.practice.basicmangodb.repository.GameCollectionRepositoryI;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                var game = getGame(dlc.gameName(), doc);

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

    @Override
    public List<DlcDTO> getAnGameDLC(String user, String game) {
        List<DlcDTO> dlcList = Collections.emptyList();

        if(gameCollectionRepositoryI.findGameDocumentByUserUsername(user).isPresent()){
            List<GameDocument> gameDocuments = gameCollectionRepositoryI.findGameDocumentByUserUsername(user).get();

            for(GameDocument doc : gameDocuments){
                var dlc = getGame(game, doc);

                if(dlc.isPresent()) {
                    dlcList = dlc.get().getDlcs().stream().map(e -> createDLcDTO(e, game)).toList();
                } else {
                    throw new NoDLCFoundException(game + " has no DLC added.");
                }
            }
        } else {
            throw new NoGamesFoundException(game + " not found.");
        }

        return dlcList;
    }

    private Optional<GameDocumentDTO> getGame(String game, GameDocument doc) {
        return doc.getGame()
                .stream()
                .filter(e -> e.getName().contentEquals(game))
                .findFirst();

//        return gamesDLC.stream()
//                .filter(e -> e.getName().contentEquals(game))
//                .findFirst();
    }

    private DlcDTO createDLcDTO(DLC dlc, String gameName){
        return new DlcDTO(gameName,
                dlc.getDlcName(),
                dlc.getReleaseDate(),
                dlc.getRating());
    }
}
