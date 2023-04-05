package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.exceptions.NoSuchCommandException;

public class CommandInvoker {
    ICommand concreteCommand;
    Map<String, ICommand> commandRegistry = new HashMap<String, ICommand>();
    public void register(String commandName,ICommand concreteCommand){
        this.commandRegistry.put(commandName, concreteCommand);
    }

    private ICommand get(String commandName){
        return commandRegistry.get(commandName);
    }


    public void executeCommand(String commandName, List<String> tokens) throws Exception{
        ICommand command = get(commandName);
        if(command == null){
            throw new NoSuchCommandException("at CommandInvoker");
        }
        command.execute(tokens);
    }
}
