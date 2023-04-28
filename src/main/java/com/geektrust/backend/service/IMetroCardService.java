package com.geektrust.backend.service;

import com.geektrust.backend.exceptions.InsufficientBalanceException;

import java.util.Optional;

public interface IMetroCardService<MetroCard> {
     void registerMetroCard(String metroCardName, long balance);
     long processJourney(String passengerCard, String originStation, boolean isReturnJourney) throws InsufficientBalanceException;
     boolean isMetroCardRegistered(String passengerCardName);
     Optional<MetroCard> getMetroCard(String  passengerCardName);
}
