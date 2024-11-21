package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.models.dto.DlcDTO;
import org.practice.basicmangodb.service.dlcservice.DLCServiceI;
import org.practice.basicmangodb.service.dlcservice.DLCServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dlccontroller")
@CrossOrigin(origins = "http://localhost:5173")
public class DLCController {

    private final DLCServiceI dlcService;

    public DLCController(DLCServiceImpl dlcService){
        this.dlcService = dlcService;
    }

    @PostMapping("/addAnDLC")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDLC(@RequestBody DlcDTO dlc,
                       @RequestParam String user){
        dlcService.addAnDLC(dlc, user);
    }

    @GetMapping("/getDLCForGame")
    @ResponseStatus(HttpStatus.OK)
    public List<DlcDTO> getAnGameDLC(@RequestParam String user,
                                     @RequestParam String game){

        return dlcService.getAnGameDLC(user, game);
    }
}
