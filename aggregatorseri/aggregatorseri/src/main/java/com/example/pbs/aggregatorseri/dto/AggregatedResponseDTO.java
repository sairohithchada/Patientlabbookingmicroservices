package com.example.pbs.aggregatorseri.dto;

public class AggregatedResponseDTO {
    private AppointmentDTO appointment;
    private PatientDTO patient;
    private LabDTO lab;
    private double distanceInKm;

    public AggregatedResponseDTO(AppointmentDTO appointment, PatientDTO patient, LabDTO lab, double distanceInKm) {
        this.appointment = appointment;
        this.patient = patient;
        this.lab = lab;
        this.distanceInKm = distanceInKm;
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public LabDTO getLab() {
        return lab;
    }

    public void setLab(LabDTO lab) {
        this.lab = lab;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }
}
