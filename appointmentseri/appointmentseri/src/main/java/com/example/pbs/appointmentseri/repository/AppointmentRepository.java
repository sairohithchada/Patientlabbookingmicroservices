package com.example.pbs.appointmentseri.repository;

import com.example.pbs.appointmentseri.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}