package com.example.pbs.patientseri.service;


import com.example.pbs.patientseri.dto.PatientDTO;
import com.example.pbs.patientseri.dto.PatientAddressDTO;
import com.example.pbs.patientseri.entity.Patient;
import com.example.pbs.patientseri.entity.PatientAddress;
import com.example.pbs.patientseri.repository.PatientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private NominatimService nominatimService;

    @Autowired
    private Validator validator;  // Jakarta Bean Validator

    public Patient registerPatient(PatientDTO dto) {
        // Validate PatientRequestDTO
        Set<ConstraintViolation<PatientDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Invalid patient data", violations);
        }

        // Validate each address DTO
        for (PatientAddressDTO addressDTO : dto.getAddresses()) {
            Set<ConstraintViolation<PatientAddressDTO>> addrViolations = validator.validate(addressDTO);
            if (!addrViolations.isEmpty()) {
                throw new ConstraintViolationException("Invalid address data", addrViolations);
            }
        }

        // Convert to Patient entity
        Patient patient = new Patient();
        patient.setFname(dto.getFname());
        patient.setLname(dto.getLname());
        patient.setEmail(dto.getEmail());
        patient.setGender(dto.getGender());
        patient.setDob(dto.getDob());
        patient.setPhonenumber(dto.getPhonenumber());

        // Map each address
        List<PatientAddress> addresses = dto.getAddresses().stream().map(addressDTO -> {
            PatientAddress address = new PatientAddress();
            address.setAddressid(UUID.randomUUID());
            address.setAddressline1(addressDTO.getAddressline1());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setCountry(addressDTO.getCountry());
            address.setZipcode(addressDTO.getZipcode());
            address.setAddresstype(addressDTO.getAddresstype());
            address.setIs_primary(Boolean.TRUE.equals(addressDTO.getIs_primary()));

            // Call Nominatim
            double[] coords = nominatimService.getCoordinates(
                    addressDTO.getAddressline1(),
                    addressDTO.getCity(),
                    addressDTO.getState(),
                    addressDTO.getCountry(),
                    addressDTO.getZipcode()
            );

            address.setLatitude(BigDecimal.valueOf(coords[0]));
            address.setLongitude(BigDecimal.valueOf(coords[1]));
            address.setPatient(patient);

            return address;
        }).collect(Collectors.toList());

        patient.setAddresses(addresses);

        return patientRepo.save(patient);
    }

    public Patient getPatientWithAddresses(int id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));
    }

    public boolean existsById(int id) {
        return patientRepo.existsById(id);
    }
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

}

