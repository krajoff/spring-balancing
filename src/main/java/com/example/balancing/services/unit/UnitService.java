package com.example.balancing.services.unit;

import com.example.balancing.models.unit.Unit;

public interface UnitService {
    Unit getUnitById(Long id);
    Unit getFilledUnitById(Long id);
    Unit createUnit(Unit unit);
    Unit updateUnit(Long id, Unit unit);
    void deleteUnit(Long id);

}
