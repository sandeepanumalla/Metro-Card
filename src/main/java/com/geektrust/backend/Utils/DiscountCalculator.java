package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroStation;

public class DiscountCalculator {

    private final double RETURN_JOURNEY_DISCOUNT_PERCENTAGE;
    private final int PERCENT_CONVERSION_FACTOR;

    public DiscountCalculator(double discountPercentage, int percentageConversionFactor) {
        this.RETURN_JOURNEY_DISCOUNT_PERCENTAGE = discountPercentage;
        this.PERCENT_CONVERSION_FACTOR = percentageConversionFactor;
    }
    public long calculateDiscount(long journeyAmount, boolean isReturnJourney,
                                  MetroStation metroStation) {
        if(RETURN_JOURNEY_DISCOUNT_PERCENTAGE <= 0 || PERCENT_CONVERSION_FACTOR <= 0) {
            return 0;
        }
        double discount = 0;
        if(isReturnJourney){
            discount = (RETURN_JOURNEY_DISCOUNT_PERCENTAGE / PERCENT_CONVERSION_FACTOR  * PERCENT_CONVERSION_FACTOR  * journeyAmount/PERCENT_CONVERSION_FACTOR);
            metroStation.updateTotalDiscounts((long)discount);
        }
        return (long)discount;
    }
}
