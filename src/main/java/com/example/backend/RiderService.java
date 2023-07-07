package com.example.backend;

import java.util.HashMap;
import java.util.Map;

public class RiderService {
    private final Map<String, Rider> riderList;

    public RiderService() {
        riderList = new HashMap<>();
    }

    public void addRider(String riderId, Location location) {
        Rider rider = new Rider(riderId, location);
        riderList.put(riderId, rider);
    }

    public Rider getRiderById(String riderId) {
        return riderList.get(riderId);
    }
}


