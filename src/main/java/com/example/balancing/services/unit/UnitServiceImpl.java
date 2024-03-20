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

    public Unit calculateTotalWeight(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found"));
        List<Record> records = unit.getRecords();
        for (Record record : records) {
            Record tempRecord = record;
            Complex totalComplexWeight = record.getComplexWeight();
            while (tempRecord.getReference() != -1) {
                Long tempId = tempRecord.getReference();
                try {
                    totalComplexWeight = totalComplexWeight.
                            plus(recordService.getRecordById(tempId).getComplexWeight());
                    tempRecord = recordService.getRecordById(tempId);
                } catch (RuntimeException e) {
                    tempRecord.setReference(-1L);
                    recordService.updateRecord(tempRecord.getId(), tempRecord);
                    System.out.println(e.getMessage());
                }
            }
            record.setComplexTotalWeight(totalComplexWeight);
        }
        unit.setRecords(records);
        return unit;
    }

    public Unit calculateTarget(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new RuntimeException("Unit not found"));
        List<Record> records = unit.getRecords();
        for (Record record : records) {
        }
        return unit;
    }
}
