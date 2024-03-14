package com.example.balancing.services.unit;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.services.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Unit getCompleteUnitById(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found"));
        List<Record> records = recordService.getCompleteRecordsByUnit(unit.getRecords());
        unit.setRecords(records);
        return unit;
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setStation(unit.getStation());
        existingUnit.setPlate(unit.getPlate());
        existingUnit.setUnitnumber(unit.getUnitnumber());
        existingUnit.setDescription(unit.getDescription());
        return unitRepository.save(existingUnit);
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

    public Unit calculationTarget(Unit unit) {

        Iterator<Record> record = unit.getRecords().iterator();
        Set<String> sets = new HashSet<>();
        while (record.hasNext()) {
            String mode = record.next().getMode();
            sets.add(mode);
        }

        Complex totalWeight;
        Record tempRecord, phaseRecord;
        List<Record> records;
        List<Unit> units;
        Integer BaseId;
        Double pTotWgt;
        Boolean initial;
        for (String mode : sets) {
            units = unitRepository.findByIdAndMode(unit.getId(), mode);
        }
        return unit;
    }
}
