package com.example.balancing.services.unit;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

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
        existingUnit.setNumber(unit.getNumber());
        existingUnit.setDescription(unit.getDescription());
        return unit;
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }
}
