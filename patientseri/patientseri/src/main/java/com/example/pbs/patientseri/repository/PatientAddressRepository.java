package com.example.pbs.patientseri.repository;

import com.example.pbs.patientseri.entity.PatientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientAddressRepository extends JpaRepository<PatientAddress, UUID> {

}