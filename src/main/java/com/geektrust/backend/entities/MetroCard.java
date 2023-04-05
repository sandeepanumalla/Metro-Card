package com.geektrust.backend.entities;

public class MetroCard extends BaseEntity{
    private String name ;
    private Long balance;
    private MetroPassenger passengerType;

    public String getName() {
        return name;
    }
    public MetroCard(String name, Long balance) {
        this.name = name;
        this.balance = balance;
    }
    public MetroCard(String name, Long balance, MetroPassenger passengerType) {
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
    public void doRecharge(Long balance) {
        this.balance += balance;
    }

    public void checkInDeduction(Long balance){
        this.balance = this.balance- balance;
    }

    public MetroPassenger getPassengerType() {
        return passengerType;
    }
    public void setPassengerType(String passengerType) {
        if(passengerType.equals("ADULT")){
            this.passengerType = MetroPassenger.ADULT;
        }
        else if(passengerType.equals("SENIOR_CITIZEN")){
            this.passengerType = MetroPassenger.SENIOR_CITIZEN;
        }
        else if(passengerType.equals("KID")){
            this.passengerType = MetroPassenger.KID;
        }
        // this.passengerType = passengerType;
    } 
    
    
}
