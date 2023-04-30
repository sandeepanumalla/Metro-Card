package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.service.IMetroCardService;

public class BalanceCommand implements ICommand{
    private static final int TOKEN_METROCARD_NAME_INDEX = 1;
    private static final int TOKEN_METROCARD_BALANCE_INDEX  = 2;
    IMetroCardService<MetroCard> metroCardService;
    public BalanceCommand(IMetroCardService<MetroCard> metroCardService){
        this.metroCardService = metroCardService;
    }

    @Override
    public void execute(List<String> tokens) {
        metroCardService.registerMetroCard(tokens.get(TOKEN_METROCARD_NAME_INDEX), Long.parseLong(tokens.get(TOKEN_METROCARD_BALANCE_INDEX)));
    }
}
