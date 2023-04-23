package com.geektrust.backend.Utils;

import com.geektrust.backend.entities.MetroCard;
public class RechargeProcessor {
    public long rechargePassengerMetroCard(MetroCard metroCard, Long amount) {
        metroCard.doRecharge(amount);
        return metroCard.getBalance();
    }
}
