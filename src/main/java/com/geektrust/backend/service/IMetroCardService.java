package com.geektrust.backend.service;

import java.util.Optional;

public interface IMetroCardService<MetroCard> {
    public void registerMetroCards(String metroCardName, long balance);
    public long walletCheckService(String passengerCard, String arrivedFromStation, boolean isReturnJourney);
    public boolean isMetroCardRegistered(String passengerCardName);
    public long rechargePassengerMetroCard(MetroCard passengerCard, Long Amount);
    public Optional<MetroCard> getMetroCard(String  passengerCardName);
}
