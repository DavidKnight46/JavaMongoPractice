package org.practice.basicmangodb.service.dlcservice;

import org.practice.basicmangodb.models.dto.DlcDTO;

import java.util.List;

public interface DLCServiceI {
    void addAnDLC(DlcDTO dlc, String user);

    void addAllDLCs(List<DlcDTO> dlcs, String user);
}
