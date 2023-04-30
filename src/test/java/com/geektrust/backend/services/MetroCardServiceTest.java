package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.service.IMetroCardService;
import com.geektrust.backend.service.MetroCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class MetroCardServiceTest {

    private IMetroCardRepository<MetroCard, String> metroCardRepository;


    private IMetroCardService<MetroCard> metroCardService;

    @BeforeEach
    void setup() {
        metroCardRepository = new MetroCardRepository();
        metroCardService = new MetroCardService(metroCardRepository);
    }

    @Test
    public void shouldRegisterMetroCard() {
        String metroCardName = "Card1";
        long walletBalance = 50L;

        metroCardService.registerMetroCard(metroCardName, walletBalance);

        Optional<MetroCard> optionalMetroCard = metroCardService.getMetroCard(metroCardName);
        Assertions.assertTrue(optionalMetroCard.isPresent());
        MetroCard metroCard = optionalMetroCard.get();
        assertEquals(metroCardName, metroCard.getName());
        assertEquals(walletBalance, metroCard.getBalance());
    }

    @Test
    void testGetMetroCardNotFound() {
        String metroCardName = "Card1";
        Optional<MetroCard> optionalMetroCard = metroCardService.getMetroCard(metroCardName);
        assertFalse(optionalMetroCard.isPresent());
    }
}


