package com.example.pbs.distanceseri.service;

import com.example.pbs.distanceseri.dto.DistanceRequestDTO;
import com.example.pbs.distanceseri.dto.DistanceResponseDTO;
import com.example.pbs.distanceseri.util.HaversineCalculator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Service
public class DistanceService {
    public DistanceResponseDTO calculateDistance(DistanceRequestDTO request) {
        double distance = HaversineCalculator.calculateDistance(
                request.getFromLat(), request.getFromLng(),
                request.getToLat(), request.getToLng()
        );
        return new DistanceResponseDTO(distance);
    }
}
