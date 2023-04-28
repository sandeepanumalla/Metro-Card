package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.service.IMetroStationService;

public class PrintSummaryCommand implements ICommand{

    IMetroStationService<MetroStation> metroStationService;

    public PrintSummaryCommand(IMetroStationService<MetroStation> metroStationService){
        this.metroStationService = metroStationService;
    }

    @Override
    public void execute(List<String> tokens) {
        metroStationService.printSummary();
    }
}
