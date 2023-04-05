package com.geektrust.backend.service;

import java.util.Optional;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.MetroStationRepository;

public class MetroCardService implements IMetroCardService<MetroCard>{

    private IMetroCardRepository<MetroCard, String> metroCardRepository;
    private MetroStationRepository metroStationRepository;
    MetroStation metroStation = null;
    private MetroCard metroCard;

    public MetroCardService(IMetroCardRepository<MetroCard, String> metroCardRepository,
    MetroStationRepository metroStationRepository
    ){
        this.metroStationRepository = metroStationRepository;
        this.metroCardRepository = metroCardRepository;
    }

    @Override
    public void registerMetroCards(String MetroCardName, long walletBalance) {
        MetroCard metroCard = new MetroCard(MetroCardName, walletBalance);
        this.metroCardRepository.save(metroCard);
    }

    @Override
    public boolean isMetroCardRegistered(String MetroCardName) {
        return getMetroCard(MetroCardName).isPresent();
    }

    public long applyDiscount(long journeyAmount, boolean isReturnJourney){
        double discount = 0;
        if(isReturnJourney){
            discount = ((double)50 / 100 * 100 * journeyAmount/100); 
            this.metroStation.updateTotalDiscounts((long)discount);
        }
        return (long)discount;
    }

    @Override
    public long walletCheckService(String passengerCard, String arrivedFromStation, boolean isReturnJourney) {
        this.metroCard = getMetroCard(passengerCard).get();
        long journeyAmount = metroStationRepository.
                        getAmountRequiredForPassengerType(metroCard.getPassengerType());
        long balance = metroCard.getBalance();
        long discount = 0;
        long rechargeAmount = 0;
        long serviceFee = 0;
        metroStation = metroStationRepository.find(arrivedFromStation)
                                    .get();
            discount = applyDiscount(journeyAmount, isReturnJourney);
            journeyAmount -= discount;
            if(balance - journeyAmount < 0){
                rechargeAmount = journeyAmount - balance;  
                 serviceFee = (long)Math.round(rechargeAmount * (double) 2 / 100);
                 rechargePassengerMetroCard(metroCard, rechargeAmount + serviceFee);
            }
                metroStation.updateCollections(serviceFee + journeyAmount);
                metroCard.checkInDeduction(journeyAmount + serviceFee);
            return rechargeAmount + serviceFee;
    }

    @Override
    public long rechargePassengerMetroCard(MetroCard passengerCard, Long amount) {
        MetroCard metroCard = metroCardRepository
                            .find(passengerCard.getName())
                            .orElseThrow(() -> 
                            new MetroCardNotFoundException(passengerCard.getName() + "not found!!"));
        metroCard.doRecharge(amount);
        return metroCard.getBalance();
    }

    @Override
    public Optional<MetroCard> getMetroCard(String MetroCardName) {
         return metroCardRepository.find(MetroCardName);
    }
}
