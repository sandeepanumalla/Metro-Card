package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.exceptions.NoSuchCommandException;

public class CommandInvoker {
    Map<Command, ICommand> commandRegistry = new HashMap<>();
    public void register(Command command,ICommand concreteCommand){
        this.commandRegistry.put(command, concreteCommand);
    }
    public ICommand get(Command commandName){
        return commandRegistry.get(commandName);
    }

    public void executeCommand(Command commandName, List<String> tokens) throws Exception{
        ICommand command = get(commandName);
        if(command == null){
            throw new NoSuchCommandException("at CommandInvoker");
        }
        command.execute(tokens);
    }
}
