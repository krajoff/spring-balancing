package com.example.balancing.services.unit;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;

import java.util.List;

public interface UnitService {
    List<Unit> getAllUnits();

    Unit getUnitById(Long id);

    Unit createUnit(Unit unit);

    Unit updateUnit(Long id, Unit unit);

    void deleteUnit(Long id);

    Unit addRecord(Long id, Record record);

    Unit getCompleteUnitById(Long id);

}
