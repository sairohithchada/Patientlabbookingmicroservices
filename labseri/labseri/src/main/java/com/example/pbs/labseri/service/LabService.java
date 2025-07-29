package com.example.pbs.labseri.service;

import com.example.pbs.labseri.dto.LabDTO;
import com.example.pbs.labseri.dto.LabSlotDTO;
import com.example.pbs.labseri.entity.Lab;
import com.example.pbs.labseri.entity.LabSlot;
import com.example.pbs.labseri.repository.LabRepository;
import com.example.pbs.labseri.repository.LabSlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private LabSlotRepository labSlotRepository;

    public Lab addLab(LabDTO dto) {
        Lab lab = new Lab();
        lab.setLabname(dto.getLabname());
        lab.setAddressline1(dto.getAddressline1());
        lab.setCity(dto.getCity());
        lab.setState(dto.getState());
        lab.setCountry(dto.getCountry());
        lab.setZipcode(dto.getZipcode());
        lab.setLatitude(dto.getLatitude());
        lab.setLongitude(dto.getLongitude());
        return labRepository.save(lab);
    }

    public LabSlot addSlot(LabSlotDTO dto) {
        Lab lab = labRepository.findById(dto.getLabid())
                .orElseThrow(() -> new RuntimeException("Lab ID not found"));

        String session = dto.getSession().toLowerCase();
        if (!List.of("morning", "afternoon", "evening").contains(session)) {
            throw new RuntimeException("Invalid session value");
        }

        LabSlot slot = new LabSlot();
        slot.setLab(lab);
        slot.setSlotDate(dto.getSlotDate());
        slot.setSession(session);
        slot.setSlotTime(dto.getSlotTime());
        return labSlotRepository.save(slot);
    }

    public List<Lab> getAllLabs() {
        return labRepository.findAll();
    }

    public List<LabSlot> getSlotsByLabId(int labid) {
        return labSlotRepository.findByLabLabid(labid);
    }

    public boolean isSlotAvailable(int slotId) {
        return labSlotRepository.existsBySlotidAndStatusIgnoreCase(slotId, "available");
    }

    @Transactional
    public void bookSlot(int slotId) {
        LabSlot slot = labSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot ID not found"));

        if (!"available".equalsIgnoreCase(slot.getStatus())) {
            throw new RuntimeException("Slot is already booked");
        }

        slot.setStatus("booked");
        labSlotRepository.save(slot);
    }
    public Lab getLabById(int id) {
        return labRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Lab not found with ID: " + id));
    }

}
