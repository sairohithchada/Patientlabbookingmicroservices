package com.example.pbs.patientseri.repository;

import com.example.pbs.patientseri.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
