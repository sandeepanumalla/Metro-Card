package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testDiscountOnReturnJourney() {
        long amount = 75;
        boolean returnJourney = true;
        long discount = discountCalculator.calculateDiscount(amount, returnJourney, metroStation);
        Mockito.verify(metroStation).updateTotalDiscounts(discount);
        Assertions.assertEquals(37, discount);
    }

    @DisplayName("should not apply 50% discount if passenger is not on return journey")
    @Test
    public void testDiscountIfNoReturnJourney() {
        long amount = 75;
        boolean returnJourney = false;
        long discount = discountCalculator.calculateDiscount(amount, returnJourney, metroStation);
        Assertions.assertEquals(0, discount);
    }
}
