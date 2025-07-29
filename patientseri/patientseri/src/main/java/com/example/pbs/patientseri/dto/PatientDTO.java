package com.example.pbs.patientseri.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class PatientDTO {
    @NotBlank
    private String fname;

    @NotBlank
    private String lname;

    @NotNull
    private LocalDate dob;

    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank
    @Size(min = 10, max = 15)
    private String phonenumber;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private List<PatientAddressDTO> addresses;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PatientAddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<PatientAddressDTO> addresses) {
        this.addresses = addresses;
    }
}