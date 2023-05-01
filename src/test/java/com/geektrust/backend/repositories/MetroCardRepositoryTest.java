package com.geektrust.backend.repositories;


import com.geektrust.backend.entities.MetroCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MetroCardRepositoryTest {
    IMetroCardRepository<MetroCard, String> metroCardRepository;

    @BeforeEach
    void setup() {
        metroCardRepository = new MetroCardRepository();
    }


    @Test
    void testFindShouldReturnEmptyOptionalIfNotPresent() throws Exception {
        String metroCardName = "MC1";
        Optional<MetroCard> metroStation = metroCardRepository.find(metroCardName);
        assertFalse(metroStation.isPresent());
    }

    @Test
    void saveMetroCard_Success() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();
        MetroCard metroCard = new MetroCard("John", 1000L);

        // Act
        metroCardRepository.save(metroCard);

        // Assert
        assertTrue(metroCardRepository.find("John").isPresent());
    }

    @Test
    void findAllMetroCards() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();
        MetroCard metroCard1 = new MetroCard("John", 1000L);
        MetroCard metroCard2 = new MetroCard("Jane", 500L);
        metroCardRepository.save(metroCard1);
        metroCardRepository.save(metroCard2);

        // Act
        List<MetroCard> metroCards = metroCardRepository.findAll();

        // Assert
        assertEquals(2, metroCards.size());
        assertTrue(metroCards.contains(metroCard1));
        assertTrue(metroCards.contains(metroCard2));
    }

    @Test
    void findMetroCard_Success() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();
        MetroCard metroCard = new MetroCard("John", 1000L);
        metroCardRepository.save(metroCard);

        // Act
        Optional<MetroCard> foundMetroCard = metroCardRepository.find("John");

        // Assert
        assertTrue(foundMetroCard.isPresent());
        assertEquals(metroCard, foundMetroCard.get());
    }

    @Test
    void findMetroCard_NotFound() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();

        // Act
        Optional<MetroCard> foundMetroCard = metroCardRepository.find("John");

        // Assert
        assertFalse(foundMetroCard.isPresent());
    }

    @Test
    void existsByIdMetroCard_Exists() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();
        MetroCard metroCard = new MetroCard("John", 1000L);
        metroCardRepository.save(metroCard);

        // Act
        boolean exists = metroCardRepository.existsById(metroCard);

        // Assert
        assertFalse(exists);
    }

    @Test
    void existsByIdMetroCard_NotExists() {
        // Arrange
        MetroCardRepository metroCardRepository = new MetroCardRepository();
        MetroCard metroCard = new MetroCard("John", 1000L);

        // Act
        boolean exists = metroCardRepository.existsById(metroCard);

        // Assert
        assertTrue(exists);
    }

}
