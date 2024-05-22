package com.example.balancing.services.unit;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.weight.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private RecordService recordService;

    @Autowired
    private WeightService weightService;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    public List<Unit> getUnitsByUserId(Long id) {
        return unitRepository.findByUserId(id);
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setStation(unit.getStation());
        existingUnit.setUnitNumber(unit.getUnitNumber());
        existingUnit.setDescription(unit.getDescription());
        return unitRepository.save(existingUnit);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

}
