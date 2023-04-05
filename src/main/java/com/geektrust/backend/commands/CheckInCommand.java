package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.service.IMetroStationService;

public class CheckInCommand implements ICommand{
    IMetroStationService metroStationService;

    public CheckInCommand(IMetroStationService metroStationService){
        this.metroStationService = metroStationService;
    }

    @Override
    public void execute(List<String> tokens) throws Exception {
        metroStationService.doCheckInProgress(tokens.get(1), tokens.get(2), tokens.get(3));
    }


}
