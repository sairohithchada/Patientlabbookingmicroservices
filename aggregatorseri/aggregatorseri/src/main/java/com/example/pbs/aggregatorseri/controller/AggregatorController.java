package com.example.pbs.aggregatorseri.controller;

import com.example.pbs.aggregatorseri.dto.AggregatedResponseDTO;
import com.example.pbs.aggregatorseri.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aggregator")
public class AggregatorController {

    @Autowired
    private AggregatorService aggregatorService;


    @GetMapping("/appointments/{appointmentId}") // ✅ FIXED: matches your request path
    public AggregatedResponseDTO getAggregatedInfo(@PathVariable int appointmentId) {
        return aggregatorService.getAggregatedDetails(appointmentId);
    }
}