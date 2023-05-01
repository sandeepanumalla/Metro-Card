package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.service.IMetroCardService;
import com.geektrust.backend.service.MetroCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
//        IMetroCardRepository<MetroCard, String> passengerJourneyRepository = new MetroCardRepository(null);
        metroCardService = new MetroCardService( metroCardRepository);
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

    @Test
    @DisplayName("should register new metro card")
    public void testRegisterMetroCard() {
        metroCardService.registerMetroCard("card1", 500);
        Optional<MetroCard> result = metroCardService.getMetroCard("card1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(500, result.get().getBalance());
    }

    @Test
    @DisplayName("should return empty optional for non-existent metro card")
    public void testGetNonExistentMetroCard() {
        Optional<MetroCard> result = metroCardService.getMetroCard("nonexistentcard");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should return registered metro card")
    public void testGetMetroCard() {
        metroCardService.registerMetroCard("card2", 1000);
        Optional<MetroCard> result = metroCardService.getMetroCard("card2");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1000, result.get().getBalance());
    }

    @Test
    @DisplayName("should register and retrieve metro card")
    public void testRegisterAndGetMetroCard() {
        // Register a new metro card
        metroCardService.registerMetroCard("card1", 1000);

        // Retrieve the registered metro card
        Optional<MetroCard> result = metroCardService.getMetroCard("card1");

        // Assert that the result is present and has the expected values
        Assertions.assertTrue(result.isPresent());
        MetroCard metroCard = result.get();
        Assertions.assertEquals("card1", metroCard.getName());
        Assertions.assertEquals(1000, metroCard.getBalance());
    }

}


