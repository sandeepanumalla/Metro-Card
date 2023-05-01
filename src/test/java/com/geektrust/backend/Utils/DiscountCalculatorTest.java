package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiscountCalculatorTest {


    DiscountCalculator discountCalculator;


    @BeforeEach
    void setup() {
         final double RETURN_JOURNEY_DISCOUNT_PERCENTAGE = 50;
         final int PERCENT_CONVERSION_FACTOR = 100;
        discountCalculator = new DiscountCalculator(RETURN_JOURNEY_DISCOUNT_PERCENTAGE, PERCENT_CONVERSION_FACTOR);
    }

    @Mock
    MetroStation metroStation;

    @DisplayName("should apply 50% discount if passenger is on return journey")
    @Test
    public void testDiscountWithReturnJourney() {
        long amount = 75;
        boolean returnJourney = true;
        long discount = discountCalculator.calculateDiscount(amount, returnJourney, metroStation);
        Mockito.verify(metroStation).updateTotalDiscounts(discount);
        assertEquals(37, discount);
    }

    @DisplayName("should not apply 50% discount if passenger is not on return journey")
    @Test
    public void testDiscountWithoutReturnJourney() {
        long amount = 75;
        boolean returnJourney = false;
        long discount = discountCalculator.calculateDiscount(amount, returnJourney, metroStation);
        assertEquals(0, discount);
    }

    @Test
    void testCalculateDiscountWithZeroJourneyAmount() {
        long journeyAmount = 0;
        boolean isReturnJourney = true;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        long expectedDiscount = 0;
        long actualDiscount = discountCalculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(expectedDiscount, actualDiscount);
        assertEquals(expectedDiscount, metroStation.getTotalDiscounts());
    }


    @Test
    void testCalculateDiscountWithNegativeDiscountPercentage() {
        long journeyAmount = 100;
        boolean isReturnJourney = true;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        discountCalculator = new DiscountCalculator(-10, 100);
        long expectedDiscount = 0;
        long actualDiscount = discountCalculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(expectedDiscount, actualDiscount);
        assertEquals(expectedDiscount, metroStation.getTotalDiscounts());
    }

    @Test
    void testCalculateDiscountWithNegativePercentageConversionFactor() {
        long journeyAmount = 100;
        boolean isReturnJourney = true;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        discountCalculator = new DiscountCalculator(10, -100);
        long expectedDiscount = 0;
        long actualDiscount = discountCalculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(expectedDiscount, actualDiscount);
        assertEquals(expectedDiscount, metroStation.getTotalDiscounts());
    }

    @Test
    public void testZeroDiscountPercentage() {
        DiscountCalculator calculator = new DiscountCalculator(0, 100);
        long journeyAmount = 500;
        boolean isReturnJourney = true;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        long discount = calculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(0, discount);
        assertEquals(0, metroStation.getTotalDiscounts());
    }

    @Test
    public void testNonReturnJourney() {
        DiscountCalculator calculator = new DiscountCalculator(10, 100);
        long journeyAmount = 500;
        boolean isReturnJourney = false;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        long discount = calculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(0, discount);
        assertEquals(0, metroStation.getTotalDiscounts());
    }

    @Test
    public void testSmallDiscountPercentage() {
        DiscountCalculator calculator = new DiscountCalculator(5, 100);
        long journeyAmount = 1000;
        boolean isReturnJourney = true;
        MetroStation metroStation = new MetroStation("1", "AIRPORT", 0, 0);
        long discount = calculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        assertEquals(50, discount);
        assertEquals(50, metroStation.getTotalDiscounts());
    }

}
