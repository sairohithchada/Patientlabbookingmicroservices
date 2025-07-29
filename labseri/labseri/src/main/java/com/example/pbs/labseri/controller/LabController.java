package com.example.pbs.labseri.controller;



import com.example.pbs.labseri.dto.LabDTO;
import com.example.pbs.labseri.dto.LabSlotDTO;
import com.example.pbs.labseri.entity.Lab;
import com.example.pbs.labseri.entity.LabSlot;
import com.example.pbs.labseri.service.LabService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labs")
public class LabController {

    @Autowired
    private LabService labService;

    @PostMapping
    public ResponseEntity<Lab> addLab(@RequestBody @Valid LabDTO dto) {
        return ResponseEntity.ok(labService.addLab(dto));
    }

    @PostMapping("/slots")
    public ResponseEntity<LabSlot> addSlot(@RequestBody @Valid LabSlotDTO dto) {
        return ResponseEntity.ok(labService.addSlot(dto));
    }

    @GetMapping
    public ResponseEntity<List<Lab>> getAllLabs() {
        return ResponseEntity.ok(labService.getAllLabs());
    }

    @GetMapping("/{id}/slots")
    public ResponseEntity<List<LabSlot>> getSlots(@PathVariable int id) {
        return ResponseEntity.ok(labService.getSlotsByLabId(id));
    }

    @GetMapping("/slots/{id}/exists")
    public ResponseEntity<Boolean> isSlotAvailable(@PathVariable int id) {
        return ResponseEntity.ok(labService.isSlotAvailable(id));
    }

    @PutMapping("/slots/{id}/book")
    public ResponseEntity<String> bookSlot(@PathVariable int id) {
        labService.bookSlot(id);
        return ResponseEntity.ok("Slot booked successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Lab> getLabById(@PathVariable int id) {
        Lab lab = labService.getLabById(id);
        return ResponseEntity.ok(lab);
    }
}
