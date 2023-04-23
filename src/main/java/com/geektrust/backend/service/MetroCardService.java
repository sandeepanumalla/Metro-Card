package com.geektrust.backend.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;
import com.geektrust.backend.repositories.MetroStationRepository;
import com.geektrust.backend.Utils.DiscountCalculator;

public class MetroCardService implements IMetroCardService<MetroCard>{

    private final IMetroCardRepository<MetroCard, String> metroCardRepository;
    private final IMetroStationRepository<MetroStation, String> metroStationRepository;

    private final IPassengerJourneyRepository passengerJourneyRepository;
    MetroStation metroStation = null;
    private final DiscountCalculator discountCalculator;
    private final RechargeProcessor rechargeProcessor;

    private static final int PERCENT_CONVERSION_FACTOR = 100;
    private static final long SERVICE_FEE_PERCENTAGE = 2;

    public MetroCardService(IMetroCardRepository<MetroCard, String> metroCardRepository,
                            IMetroStationRepository<MetroStation, String> metroStationRepository,
                            IPassengerJourneyRepository passengerJourneyRepository
    ){
        this.metroStationRepository = metroStationRepository;
        this.metroCardRepository = metroCardRepository;
        this.discountCalculator = new DiscountCalculator();
        this.rechargeProcessor = new RechargeProcessor();
        this.passengerJourneyRepository = passengerJourneyRepository;
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

    @Override
    public long processJourney(String passengerCard, String originStation, boolean isReturnJourney) {
        MetroCard metroCard = getMetroCard(passengerCard).orElseThrow(() -> new MetroCardNotFoundException(passengerCard));
        long journeyAmount = passengerJourneyRepository.getAmountRequiredForPassengerType(metroCard.getPassengerType());
        long balance = metroCard.getBalance();
        long rechargeAmount = 0;
        long transactionFee  = 0;
        metroStation = metroStationRepository.find(originStation).orElseThrow(()-> new NoSuchElementException(originStation + "Metro Station not found"));
        long discount = discountCalculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        journeyAmount = journeyAmount - discount;
        if(balance < journeyAmount){
            rechargeAmount = journeyAmount - balance;
            transactionFee = Math.round((float) (rechargeAmount * SERVICE_FEE_PERCENTAGE) / PERCENT_CONVERSION_FACTOR);
            rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount + transactionFee);
        }
        metroStation.updateCollections(transactionFee + journeyAmount);
        metroCard.checkInDeduction(journeyAmount + transactionFee);
        return rechargeAmount + transactionFee;
    }

    @Override
    public Optional<MetroCard> getMetroCard(String MetroCardName) {
        return metroCardRepository.find(MetroCardName);
    }
}
