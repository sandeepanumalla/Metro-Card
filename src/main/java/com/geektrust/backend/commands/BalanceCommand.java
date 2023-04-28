package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.service.IMetroCardService;

public class BalanceCommand implements ICommand{

    IMetroCardService<MetroCard> metroCardService;
    public BalanceCommand(IMetroCardService<MetroCard> metroCardService){
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        metroCardService.registerMetroCard(tokens.get(1), Long.parseLong(tokens.get(2)));
    }
}
