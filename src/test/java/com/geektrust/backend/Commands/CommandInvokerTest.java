package com.geektrust.backend.Commands;

import com.geektrust.backend.commands.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CommandInvokerTest {
    private CommandInvoker commandInvoker;

    @BeforeEach
    void setUp() {
        commandInvoker = new CommandInvoker();
    }

    @Test
    void testRegister() {
        // Test that a command is successfully registered with a concrete command
        Command command = Command.BALANCE;
        ICommand concreteCommand = new BalanceCommand(null);
        commandInvoker.register(command, concreteCommand);
        assertEquals(concreteCommand, commandInvoker.get(command));
    }

    @Test
    void testGet() {
        // Test that a registered command can be retrieved successfully
        Command command = Command.BALANCE;
        ICommand concreteCommand = new BalanceCommand(null);
        commandInvoker.register(command, concreteCommand);
        assertEquals(concreteCommand, commandInvoker.get(command));

        // Test that a non-registered command returns null
        Command nonExistentCommand = Command.PRINT_SUMMARY;
        assertNull(commandInvoker.get(nonExistentCommand));
    }
}
