package com.example.balancing.controllers.api;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Unit", description = "The Unit API")
@RestController
@RequestMapping("/api/units")
public class ApiUnitController {
    @Autowired
    private UnitService unitService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    @DeleteMapping("/{unit_id}")
    public void deleteUnit(Long unit_id) {
        unitService.deleteUnit(unit_id);
    }

    @PostMapping
    public Unit createUnit(@RequestBody Unit unit) {
        return unitService.createUnit(unit);
    }

    @PutMapping("/{unit_id}")
    public Unit updateUnit(@PathVariable Long unit_id,
                           @RequestBody Unit unit) {
        return unitService.updateUnit(unit_id, unit);
    }

    @GetMapping("/{unit_id}")
    public Unit getRecordsByUnit(@PathVariable Long unit_id) {
        return unitService.calculateTotalWeight(unit_id);
    }

    @PostMapping("/{unit_id}")
    public Unit calculateTargetWeight(@PathVariable Long unit_id) {
        return unitService.calculateTotalWeight(unit_id);
    }

    @DeleteMapping("/{unit_id}/{record_id}")
    public void deleteRecord(@PathVariable Long unit_id,
                             @PathVariable Long record_id) {
        recordService.deleteRecord(record_id);
    }
}
