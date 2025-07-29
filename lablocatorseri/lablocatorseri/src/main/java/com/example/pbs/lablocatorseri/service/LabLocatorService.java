package com.example.pbs.lablocatorseri.service;

import com.example.pbs.lablocatorseri.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabLocatorService {

    @Autowired
    private RestTemplate restTemplate;

    private final String PATIENT_URL = "http://localhost:8081/patients/";
    private final String LABS_URL = "http://localhost:8082/labs";
    private final String DISTANCE_URL = "http://localhost:8084/distance/calculate";

    public LabDTO getNearestLab(int patientId) {
        // Get patient and primary address
        PatientDTO patient = restTemplate.getForObject(PATIENT_URL + patientId, PatientDTO.class);
        AddressDTO primaryAddress = patient.getAddresses().stream()
                .filter(AddressDTO::isIs_primary)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Primary address not found"));

        // Get all labs
        LabDTO[] labs = restTemplate.getForObject(LABS_URL, LabDTO[].class);

        // Find the nearest lab
        return List.of(labs).stream()
                .map(lab -> {
                    DistanceRequestDTO req = new DistanceRequestDTO();
                    req.setFromLat(primaryAddress.getLatitude());
                    req.setFromLng(primaryAddress.getLongitude());
                    req.setToLat(lab.getLatitude());
                    req.setToLng(lab.getLongitude());

                    DistanceResponseDTO res = restTemplate.postForObject(DISTANCE_URL, req, DistanceResponseDTO.class);
                    lab.setDistanceInKm(res != null ? res.getDistanceInKm() : Double.MAX_VALUE);
                    return lab;
                })
                .min(Comparator.comparingDouble(LabDTO::getDistanceInKm))
                .orElseThrow(() -> new RuntimeException("No labs found"));
    }
}