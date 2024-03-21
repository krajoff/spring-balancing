package com.example.balancing.services.unit;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
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
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        List<Record> records = unit.getRecords();
        for (Record record : records) {
            Record tempRecord = record;
            Complex totalComplexWeight = record.getComplexWeight();
            Long tempId;
            while (tempRecord.getReference() != -1) {
                tempId = tempRecord.getReference();
                try {
                    totalComplexWeight = totalComplexWeight.
                            plus(recordService
                                    .getRecordById(tempId)
                                    .getComplexWeight());
                    tempRecord = recordService
                            .getRecordById(tempId);
                } catch (RuntimeException e) {
                    tempRecord.setReference(-1L);
                    recordService.updateRecord(tempRecord.getId(),
                            tempRecord);
                    System.out.println(e.getMessage());
                }
            }
            record.setComplexTotalWeight(totalComplexWeight);
        }
        unit.setRecords(records);
        return unit;
    }

    public Unit calculateTargetWeight(Long id) {
        Unit unit = calculateTotalWeight(id);
        List<Record> records = unit.getRecords();
        Record secondRecord;
        long secondId;
        double mSensitivity, pSensitivity;
        Complex zVib1, zVib2, zWgt1, zWgt2, dVib, dWgt, sen, foo1, foo2;
        Complex targetWeight;
        for (Record firstRecord : records) {
            secondId = firstRecord.getReference();
            if (secondId != -1) {
                try {
                    secondRecord = recordService.getRecordById(secondId);
                    zVib1 = firstRecord.getComplexVibration();
                    zVib2 = secondRecord.getComplexVibration();
                    zWgt1 = firstRecord.getComplexTotalWeight();
                    zWgt2 = secondRecord.getComplexTotalWeight();
                    dVib = zVib2.minus(zVib1);
                    dWgt = zWgt2.minus(zWgt1);
                    sen = dVib.divides(dWgt);
                    mSensitivity = sen.abs();
                    pSensitivity = Math.toDegrees(sen.phase());
                    foo1 = dWgt.times(zVib1);
                    foo2 = foo1.divides(dVib);
                    targetWeight = zWgt1.minus(foo2);
                    firstRecord.setComplexTargetWeight(targetWeight);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    firstRecord
                            .setComplexTargetWeight(
                                    new Complex(0d, 0d));
                }
            } else {
                firstRecord.setComplexTargetWeight(new Complex(0d, 0d));
            }
        }
        unit.setRecords(records);
        return unit;
    }
}
