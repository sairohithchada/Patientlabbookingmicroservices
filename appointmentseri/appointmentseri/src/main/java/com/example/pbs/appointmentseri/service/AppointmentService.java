package com.example.pbs.appointmentseri.service;

import com.example.pbs.appointmentseri.dto.AppointmentRequestDTO;
import com.example.pbs.appointmentseri.dto.AppointmentResponseDTO;
import com.example.pbs.appointmentseri.entity.Appointment;
import com.example.pbs.appointmentseri.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PATIENT_SERVICE_URL = "http://localhost:8081/patients/exists/";
    private final String SLOT_CHECK_URL = "http://localhost:8082/labs/slots/";
    private final String SLOT_BOOK_URL = "http://localhost:8082/labs/slots/{id}/book";

    public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO dto) {

        // 1. Validate patient
        Boolean patientExists = restTemplate.getForObject(PATIENT_SERVICE_URL + dto.getPatientid(), Boolean.class);
        if (patientExists == null || !patientExists) {
            throw new NoSuchElementException("Patient not found with ID: " + dto.getPatientid());
        }

        // 2. Check slot availability (corrected URL)
        Boolean slotAvailable = restTemplate.getForObject(SLOT_CHECK_URL + dto.getSlotid() + "/exists", Boolean.class);
        if (slotAvailable == null || !slotAvailable) {
            throw new IllegalStateException("Slot is not available or doesn't exist.");
        }

        // 3. Book the slot
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> bookResponse = restTemplate.exchange(
                SLOT_BOOK_URL,
                HttpMethod.PUT,
                entity,
                String.class,
                dto.getSlotid()
        );

        if (!bookResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to book slot: " + bookResponse.getBody());
        }

        // 4. Save appointment
        Appointment appointment = new Appointment();
        appointment.setPatientid(dto.getPatientid());
        appointment.setLabid(dto.getLabid());
        appointment.setSlotid(dto.getSlotid());
        appointment.setAppointmentType(dto.getAppointmentType());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        Appointment saved = appointmentRepository.save(appointment);

        // 5. Prepare response
        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentid(saved.getAppointmentid());
        response.setPatientid(saved.getPatientid());
        response.setLabid(saved.getLabid());
        response.setSlotid(saved.getSlotid());
        response.setAppointmentType(saved.getAppointmentType());
        response.setAppointmentDate(saved.getAppointmentDate());
        response.setAppointmentTime(saved.getAppointmentTime());

        return response;
    }
    public AppointmentResponseDTO getAppointmentById(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with ID: " + id));

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentid(appointment.getAppointmentid());
        response.setPatientid(appointment.getPatientid());
        response.setLabid(appointment.getLabid());
        response.setSlotid(appointment.getSlotid());
        response.setAppointmentType(appointment.getAppointmentType());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());

        return response;
    }

}
