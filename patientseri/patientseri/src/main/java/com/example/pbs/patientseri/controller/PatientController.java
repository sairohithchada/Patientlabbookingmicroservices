package com.example.pbs.patientseri.controller;



import com.example.pbs.patientseri.dto.PatientDTO;
import com.example.pbs.patientseri.entity.Patient;
import com.example.pbs.patientseri.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // ✅ Register a new patient with address(es)
    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientDTO dto) {
        Patient savedPatient = patientService.registerPatient(dto);
        return ResponseEntity.ok(savedPatient);
    }

    // ✅ Get patient details by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable int id) {
        Patient patient = patientService.getPatientWithAddresses(id);
        return ResponseEntity.ok(patient);
    }

    // ✅ Check if patient exists by ID
    @GetMapping("/exists/{id}")
    public ResponseEntity<?> checkExists(@PathVariable int id) {
        boolean exists = patientService.existsById(id);
        return ResponseEntity.ok(exists);
    }
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}
