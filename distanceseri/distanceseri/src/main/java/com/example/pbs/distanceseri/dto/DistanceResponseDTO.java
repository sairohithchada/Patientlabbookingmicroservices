package com.example.pbs.distanceseri.dto;

public class DistanceResponseDTO {
    private double distanceInKm;

    public DistanceResponseDTO(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }
}