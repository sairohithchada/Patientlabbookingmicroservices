package com.example.pbs.labseri.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_slots")
public class LabSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotid;

    @ManyToOne
    @JoinColumn(name = "labid", nullable = false)
    private Lab lab;

    @NotNull(message = "Slot date is required")
    private LocalDate slotDate;

    @NotNull(message = "Session is required")
    private String session;

    @NotNull(message = "Slot time is required")
    private LocalTime slotTime;

    private String status = "available";

    private LocalDateTime createdat = LocalDateTime.now();

    public int getSlotid() {
        return slotid;
    }

    public void setSlotid(int slotid) {
        this.slotid = slotid;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public LocalTime getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(LocalTime slotTime) {
        this.slotTime = slotTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }
}
