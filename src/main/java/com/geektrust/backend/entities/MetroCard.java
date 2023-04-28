package com.geektrust.backend.entities;

import com.geektrust.backend.exceptions.InsufficientBalanceException;

public class MetroCard extends BaseEntity{
    private String name ;
    private Long balance;
    private PassengerType passengerType;

    public String getName() {
        return name;
    }
    public MetroCard(String name, Long balance) {
        this.name = name;
        this.balance = balance;
    }
    public MetroCard(String name, Long balance, PassengerType passengerType) {
        this.name = name;
        this.balance = balance;
        this.passengerType = passengerType;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getBalance() {
        return balance;
    }
    public void doRecharge(Long balance) throws IllegalArgumentException, NullPointerException {
        if(balance == null) {
            throw new NullPointerException("Balance is null");
        }
        if(balance < 0) {
            throw new IllegalArgumentException("Invalid amount" + balance);
        }
        this.balance += balance;
    }

    public void deductFare(Long fare) throws InsufficientBalanceException {
        if(fare > balance) {
            throw new InsufficientBalanceException("Insufficient balance. Please recharge your card.");
        }
        this.balance -= fare;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }
    public void setPassengerType(String passengerType) {
        switch (passengerType) {
            case "ADULT":
                this.passengerType = PassengerType.ADULT;
                break;
            case "SENIOR_CITIZEN":
                this.passengerType = PassengerType.SENIOR_CITIZEN;
                break;
            case "KID":
                this.passengerType = PassengerType.KID;
                break;
        }
    }
}
