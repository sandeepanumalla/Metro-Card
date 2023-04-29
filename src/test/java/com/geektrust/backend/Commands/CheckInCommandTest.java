package com.geektrust.backend.Commands;

import com.geektrust.backend.commands.CheckInCommand;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.service.IMetroStationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckInCommandTest {

    @Mock
    IMetroStationService<MetroStation> metroStationService;


    @DisplayName("check if checkIn command calls the doCheckInProgress")
    @Test
    void testCheckInCommand() throws Exception {
        String metroCardName = "MC1";
        String arrivedFrom = "CENTRAL";
        PassengerType passengerType = PassengerType.ADULT;
        List<String> tokens = new ArrayList<>(){{
            add("");
            add(passengerType.toString());
            add(metroCardName);
            add(arrivedFrom);
        }};

        CheckInCommand checkInCommand = new CheckInCommand(metroStationService);
        doNothing().when(metroStationService).updatePassengerJourneyProgress(passengerType.toString(),metroCardName,arrivedFrom);
        checkInCommand.execute(tokens);
        verify(metroStationService, times(1)).updatePassengerJourneyProgress(passengerType.toString(), metroCardName, arrivedFrom);
    }
}
