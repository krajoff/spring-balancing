package com.example.balancing.services.unit;

import com.example.balancing.models.unit.Unit;

import java.util.List;

public interface UnitService {
    List<Unit> getAllUnits();

    Unit getUnitById(Long id);

    Unit createUnit(Unit unit);

    Unit updateUnit(Long id, Unit unit);

    void deleteUnit(Long id);

    Unit calculateTotalWeight(Long id);

    Unit calculateTargetWeight(Long id);

    List<Unit> getUnitsByUserId(Long Id);

}
