package com.geektrust.backend.service;

import java.util.*;

import com.geektrust.backend.Utils.DiscountCalculator;
import com.geektrust.backend.Utils.RechargeProcessor;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.MetroStation;
import com.geektrust.backend.exceptions.InsufficientBalanceException;
import com.geektrust.backend.exceptions.MetroCardNotFoundException;
import com.geektrust.backend.repositories.IMetroStationRepository;
import com.geektrust.backend.repositories.IPassengerJourneyRepository;

public class MetroStationService implements IMetroStationService<MetroStation>{
    private final IMetroStationRepository<MetroStation, String> metroStationRepository;
    private final IMetroCardService<MetroCard> metroCardService;
    private final IPassengerJourneyService passengerJourneyService;
    private final int PERCENT_CONVERSION_FACTOR;
    private final long SERVICE_FEE_PERCENTAGE;
    private final IPassengerJourneyRepository passengerJourneyRepository;
    MetroStation metroStation = null;
    private final DiscountCalculator discountCalculator;
    private final RechargeProcessor rechargeProcessor;

    public MetroStationService(IMetroStationRepository<MetroStation, String> metroStationRepository,
                               IMetroCardService<MetroCard> metroCardService,
                               IPassengerJourneyService passengerJourneyService,
                               IPassengerJourneyRepository passengerJourneyRepository,
                               DiscountCalculator discountCalculator, RechargeProcessor rechargeProcessor,
                               int percentConversionFactor,
                               long serviceFeePercentage
                               ){
        this.metroStationRepository = metroStationRepository;
        this.metroCardService = metroCardService;
        this.passengerJourneyService = passengerJourneyService;
        this.passengerJourneyRepository = passengerJourneyRepository;
        this.discountCalculator = discountCalculator;
        this.rechargeProcessor = rechargeProcessor;
        this.PERCENT_CONVERSION_FACTOR = percentConversionFactor;
        this.SERVICE_FEE_PERCENTAGE = serviceFeePercentage;
    }

    @Override
    public MetroStation getMetroStation(String metroStation) {
        return metroStationRepository.find(metroStation).orElseThrow(()->
                new NoSuchElementException(metroStation + " metro station not found"));
    }

    @Override
    public long calculateJourneyFare(MetroCard metroCard, String originStation, boolean isReturnJourney) {
        long journeyAmount = passengerJourneyRepository.getFareByPassengerType(metroCard.getPassengerType());
        metroStation = metroStationRepository.find(originStation).orElseThrow(() -> new NoSuchElementException(originStation + "Metro Station not found"));
        long discount = discountCalculator.calculateDiscount(journeyAmount, isReturnJourney, metroStation);
        return journeyAmount - discount;
    }

    @Override
    public void deductJourneyFare(String passengerCard, String originStation, boolean isReturnJourney) throws InsufficientBalanceException {
        MetroCard metroCard = metroCardService.getMetroCard(passengerCard).orElseThrow(() -> new MetroCardNotFoundException(passengerCard));
        long journeyAmount = calculateJourneyFare(metroCard, originStation, isReturnJourney);
        long balance = metroCard.getBalance();
        long rechargeAmount;
        long transactionFee  = 0;
        if(balance < journeyAmount){
            rechargeAmount = journeyAmount - balance;
            transactionFee = Math.round((float) (rechargeAmount * SERVICE_FEE_PERCENTAGE) / PERCENT_CONVERSION_FACTOR);
            rechargeProcessor.rechargePassengerMetroCard(metroCard, rechargeAmount + transactionFee);
        }
        metroStation.updateCollections(transactionFee + journeyAmount);
        metroCard.deductFare(journeyAmount + transactionFee);
    }

    @Override
    public void updatePassengerJourneyProgress(String passengerCard, String passengerType, String originStation) throws InsufficientBalanceException {
        MetroCard metroCard = metroCardService
                .getMetroCard(passengerCard)
                .orElseThrow(() ->
                        new MetroCardNotFoundException("Metro Card not found"));
        metroCard.setPassengerType(passengerType);
        boolean isReturnJourney = passengerJourneyService.isReturnJourney(metroCard);
        deductJourneyFare(passengerCard, originStation, isReturnJourney);
        getMetroStation(originStation).updatePassengersTravelledSummary(metroCard);
    }
}