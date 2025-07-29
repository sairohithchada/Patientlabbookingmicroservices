//package com.example.pbs.aggregatorseri.service;
//
//import com.example.pbs.aggregatorseri.dto.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class AggregatorService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final String APPOINTMENT_URL = "http://localhost:8083/appointments/";
//    private final String PATIENT_URL = "http://localhost:8081/patients/";
//    private final String LAB_URL = "http://localhost:8082/labs/";
//    private final String DISTANCE_URL = "http://localhost:8084/distance/calculate";
//
//    public AggregatedResponseDTO getAggregatedDetails(int appointmentId) {
//        AppointmentDTO appointment = restTemplate.getForObject(APPOINTMENT_URL + appointmentId, AppointmentDTO.class);
//        PatientDTO patient = restTemplate.getForObject(PATIENT_URL + appointment.getPatientid(), PatientDTO.class);
//        LabDTO lab = restTemplate.getForObject(LAB_URL + appointment.getLabid(), LabDTO.class);
//
//        AddressDTO primaryAddress = patient.getAddresses().stream()
//                .filter(AddressDTO::isIs_primary)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Primary address not found"));
//
//        DistanceRequestDTO request = new DistanceRequestDTO(
//                primaryAddress.getLatitude(),
//                primaryAddress.getLongitude(),
//                lab.getLatitude(),
//                lab.getLongitude()
//        );
//
//        DistanceResponseDTO distance = restTemplate.postForObject(DISTANCE_URL, request, DistanceResponseDTO.class);
//
//        return new AggregatedResponseDTO(appointment, patient, lab, distance.getDistanceInKm());
//    }
//}


package com.example.pbs.aggregatorseri.service;

import com.example.pbs.aggregatorseri.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AggregatorService {

    @Autowired
    private RestTemplate restTemplate;

    private final String APPOINTMENT_URL = "http://localhost:8083/appointments/";
    private final String PATIENT_URL = "http://localhost:8081/patients/";
    private final String LAB_URL = "http://localhost:8082/labs/";
    private final String DISTANCE_URL = "http://localhost:8084/distance/calculate";

    public AggregatedResponseDTO getAggregatedDetails(int appointmentId) {
        // 1. Get appointment
        AppointmentDTO appointment = restTemplate.getForObject(APPOINTMENT_URL + appointmentId, AppointmentDTO.class);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found for ID: " + appointmentId);
        }

        // 2. Get patient details
        PatientDTO patient = restTemplate.getForObject(PATIENT_URL + appointment.getPatientid(), PatientDTO.class);
        if (patient == null) {
            throw new RuntimeException("Patient not found with ID: " + appointment.getPatientid());
        }

        // 3. Get lab details
        LabDTO lab = restTemplate.getForObject(LAB_URL + appointment.getLabid(), LabDTO.class);
        if (lab == null) {
            throw new RuntimeException("Lab not found with ID: " + appointment.getLabid());
        }

        // 4. Get primary address from patient
        AddressDTO primaryAddress = patient.getAddresses().stream()
                .filter(AddressDTO::isIs_primary)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Primary address not found for patient"));

        // 5. Call distance service
        DistanceRequestDTO request = new DistanceRequestDTO(
                primaryAddress.getLatitude(),
                primaryAddress.getLongitude(),
                lab.getLatitude(),
                lab.getLongitude()
        );

        DistanceResponseDTO distanceResponse = restTemplate.postForObject(DISTANCE_URL, request, DistanceResponseDTO.class);
        double distanceInKm = distanceResponse != null ? distanceResponse.getDistanceInKm() : -1;

        // 6. Return aggregated response
        return new AggregatedResponseDTO(appointment, patient, lab, distanceInKm);
    }
}
