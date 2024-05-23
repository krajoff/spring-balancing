package com.example.balancing.controllers.api;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import com.example.balancing.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all units")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/user/{username}")
    @Operation(summary = "Get units by username")
    public List<Unit> getUnitsByUser(String username) {
        return unitService
                .getUnitsByUserId(userService
                        .getUserByUsername(username)
                        .getId());
    }

    @DeleteMapping("/{unit_id}")
    @Operation(summary = "Delete an unit by id")
    public void deleteUnit(Long unit_id) {
        unitService.deleteUnit(unit_id);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new unit")
    public Unit createUnit(@RequestBody Unit unit) {
        return unitService.createUnit(unit);
    }

    @PutMapping("/update/{unit_id}")
    @Operation(summary = "Update an unit")
    public Unit updateUnit(@PathVariable Long unit_id,
                           @RequestBody Unit unit) {
        return unitService.updateUnit(unit_id, unit);
    }

    @DeleteMapping("/{unit_id}/{record_id}")
    @Operation(summary = "Delete record by id for definite an unit")
    public void deleteRecord(@PathVariable Long unit_id,
                             @PathVariable Long record_id) {
        recordService.deleteRecord(record_id);
    }
}
