package com.geektrust.backend.Commands;

import com.geektrust.backend.commands.BalanceCommand;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.service.IMetroCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceCommandTest {

    @Mock
    IMetroCardService<MetroCard> metroCardService;
    @DisplayName("check if balance command calls the register metro card")
    @Test
    void testBalanceCommand() {
        String metroCardName = "MC1";
        String initialBalance = "400";
        List<String> tokens = new ArrayList<>(){{
            add("");
            add(metroCardName);
            add(initialBalance);
        }};

        BalanceCommand balanceCommand = new BalanceCommand(metroCardService);

        doNothing().when(metroCardService).registerMetroCard(metroCardName, Long.parseLong(initialBalance));
        balanceCommand.execute(tokens);
        verify(metroCardService, times(1)).registerMetroCard(metroCardName, Long.parseLong(initialBalance));
    }
}
