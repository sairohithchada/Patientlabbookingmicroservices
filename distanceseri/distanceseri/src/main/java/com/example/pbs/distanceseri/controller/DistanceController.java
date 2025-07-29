package com.example.pbs.distanceseri.controller;

import com.example.pbs.distanceseri.dto.DistanceRequestDTO;
import com.example.pbs.distanceseri.dto.DistanceResponseDTO;
import com.example.pbs.distanceseri.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distance")
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @PostMapping("/calculate")
    public DistanceResponseDTO calculate(@RequestBody DistanceRequestDTO request) {
        return distanceService.calculateDistance(request);
    }
}