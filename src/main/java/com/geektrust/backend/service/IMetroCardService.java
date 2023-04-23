package com.geektrust.backend.service;

import java.util.Optional;

public interface IMetroCardService<MetroCard> {
     void registerMetroCards(String metroCardName, long balance);
     long processJourney(String passengerCard, String originStation, boolean isReturnJourney);
     boolean isMetroCardRegistered(String passengerCardName);
     Optional<MetroCard> getMetroCard(String  passengerCardName);
}
