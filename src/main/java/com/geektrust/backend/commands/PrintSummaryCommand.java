package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.service.IMetroStationService;

public class PrintSummaryCommand implements ICommand{

    IMetroStationService metroStationService;

    public PrintSummaryCommand(IMetroStationService metroStationService){
        this.metroStationService = metroStationService;
    }

    @Override
    public void execute(List<String> tokens) {
        metroStationService.printSummary();
    }
    
}
