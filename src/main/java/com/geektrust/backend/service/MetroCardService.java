package com.geektrust.backend.service;

import java.util.Optional;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.repositories.IMetroCardRepository;
public class MetroCardService implements IMetroCardService<MetroCard>{

    private final IMetroCardRepository<MetroCard, String> metroCardRepository;

    public MetroCardService(IMetroCardRepository<MetroCard, String> metroCardRepository){
        this.metroCardRepository = metroCardRepository;
    }

    @Override
    public void registerMetroCard(String MetroCardName, long walletBalance) {
        MetroCard metroCard = new MetroCard(MetroCardName, walletBalance);
        this.metroCardRepository.save(metroCard);
    }

    @Override
    public Optional<MetroCard> getMetroCard(String MetroCardName) {
        return metroCardRepository.find(MetroCardName).map(Optional::of).orElseGet(Optional::empty);
    }
}
