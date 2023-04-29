package com.geektrust.backend.commands;

import java.util.List;

import com.geektrust.backend.service.IConsolePrinterService;

public class PrintSummaryCommand implements ICommand{

    IConsolePrinterService consolePrinterService;

    public PrintSummaryCommand(IConsolePrinterService consolePrinterService){
        this.consolePrinterService = consolePrinterService;
    }

    @Override
    public void execute(List<String> tokens) {
        consolePrinterService.printSummary();
    }
}
