package com.example.pbs.lablocatorseri.controller;

import com.example.pbs.lablocatorseri.dto.LabDTO;
import com.example.pbs.lablocatorseri.service.LabLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/lab-locator")
public class LabLocatorController {

    @Autowired
    private LabLocatorService labLocatorService;

    @GetMapping("/nearest/{patientId}")
    public List<LabDTO> getNearestLabs(@PathVariable int patientId) {
        return Collections.singletonList(labLocatorService.getNearestLab(patientId));
    }
}