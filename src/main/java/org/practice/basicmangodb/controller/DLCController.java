package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.models.dto.DlcDTO;
import org.practice.basicmangodb.service.dlcservice.DLCServiceI;
import org.practice.basicmangodb.service.dlcservice.DLCServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dlccontroller")
public class DLCController {

    private final DLCServiceI dlcService;

    public DLCController(DLCServiceImpl dlcService){
        this.dlcService = dlcService;
    }

    @PostMapping("/addAnDLC")
    @ResponseStatus(HttpStatus.OK)
    public void addDLC(@RequestBody DlcDTO dlc, @RequestParam String user){
        dlcService.addAnDLC(dlc, user);
    }
}
