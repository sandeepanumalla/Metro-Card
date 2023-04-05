package com.geektrust.backend.entities;

import java.util.HashMap;
import java.util.Map;

public class MetroStation extends BaseEntity {
    private String name;
    private long Collections;
    private long TotalDiscounts;
    private Map<MetroCard, Integer> passengersTravelledSummary = new HashMap<>();

    public Map<MetroCard, Integer> getPassengersTravelledSummary() {
        return passengersTravelledSummary;
    }


    public void setPassengersTravelledSummary(Map<MetroCard, Integer> passengersTravelledSummary) {
        this.passengersTravelledSummary = passengersTravelledSummary;
    }


    public MetroStation(String id, String name, long collections, long totalDiscounts) {
            this.id = id;
            this.name = name;
            Collections = collections;
            TotalDiscounts = totalDiscounts;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getCollections() {
        return Collections;
    }
    public void updateCollections(long collections) {
        Collections += collections;
    }
    public long getTotalDiscounts() {
        return TotalDiscounts;
    }
    public void updateTotalDiscounts(long totalDiscounts) {
        TotalDiscounts += totalDiscounts;
    }
    
    public void updatePassengersTravelledSummary(MetroCard metroCard) {
            this.getPassengersTravelledSummary().computeIfPresent(metroCard, 
            (K, V) -> V += 1);

            this.getPassengersTravelledSummary().computeIfAbsent(metroCard, 
            (K) -> 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetroStation other = (MetroStation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MetroStation [Total Collections=" + Collections + ", TotalDiscounts=" + TotalDiscounts
                + ", name=" + name + "]";
    }
}
