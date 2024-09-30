package com.example.balancing.services.unit;

import com.example.balancing.models.unit.Unit;

import java.util.List;

public interface UnitService {
    Unit getUnitById(Long id);
    List<Unit> getUnitsByEmail(String email);

    Unit createUnit(Unit unit);

    Unit updateUnit(Long id, Unit unit);

    void deleteUnit(Long id);
}
