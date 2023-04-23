package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroStation;

public class DiscountCalculator {

    private static final double RETURN_JOURNEY_DISCOUNT_PERCENTAGE = 50.0;
    private static final int PERCENT_CONVERSION_FACTOR = 100;

    public long calculateDiscount(long journeyAmount, boolean isReturnJourney,
                                  MetroStation metroStation) {
        double discount = 0;
        if(isReturnJourney){
            discount = (RETURN_JOURNEY_DISCOUNT_PERCENTAGE / PERCENT_CONVERSION_FACTOR  * PERCENT_CONVERSION_FACTOR  * journeyAmount/PERCENT_CONVERSION_FACTOR);
            metroStation.updateTotalDiscounts((long)discount);
        }
        return (long)discount;
    }

}
