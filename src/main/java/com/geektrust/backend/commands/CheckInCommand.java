package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.service.IMetroStationService;

public class CheckInCommand implements ICommand{
    IMetroStationService<MetroStation> metroStationService;
    private static final int TOKEN_METROCARD_NAME_INDEX = 1;
    private static final int TOKEN_PASSENGER_TYPE_INDEX = 2;
    private static final int TOKEN_ORIGIN_STATION_INDEX = 3;

    public CheckInCommand(IMetroStationService<MetroStation> metroStationService){
        this.metroStationService = metroStationService;
    }

    @Override
    public void execute(List<String> tokens) throws Exception {
        metroStationService.updatePassengerJourneyProgress(tokens.get(TOKEN_METROCARD_NAME_INDEX), tokens.get(TOKEN_PASSENGER_TYPE_INDEX), tokens.get(TOKEN_ORIGIN_STATION_INDEX));
    }
}
