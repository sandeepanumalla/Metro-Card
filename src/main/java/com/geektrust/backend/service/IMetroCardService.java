package com.geektrust.backend.service;

import java.util.Optional;

public interface IMetroCardService<MetroCard> {
     void registerMetroCard(String metroCardName, long balance);
     Optional<MetroCard> getMetroCard(String  passengerCardName);
}
