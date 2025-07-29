package com.example.pbs.labseri.repository;

import com.example.pbs.labseri.entity.LabSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LabSlotRepository extends JpaRepository<LabSlot, Integer> {
    List<LabSlot> findByLabLabid(int labid);
    boolean existsBySlotidAndStatusIgnoreCase(int slotid, String status);
}