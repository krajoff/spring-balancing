package com.example.balancing.services.unit;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.services.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private RecordService recordService;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setStation(unit.getStation());
        existingUnit.setUnitnumber(unit.getUnitnumber());
        existingUnit.setDescription(unit.getDescription());
        return unit;
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    public Unit addRecord(Long id, Record record) {
        Unit existingUnit = getUnitById(id);
        record.setUnit(existingUnit);
        recordService.createRecord(record);
        existingUnit.addRecord(record);
        return unitRepository.save(existingUnit);
    }
}
