package com.example.balancing.controller.api;


import com.example.balancing.dto.UnitDto;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.service.unit.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stations/{stationId}/units")
public class UnitController {

    private final UnitService unitService;

    @GetMapping()
    public ResponseEntity<List<UnitDto>> getByStation(@PathVariable UUID stationId) {
        return ResponseEntity.ok(unitService.getByStation(stationId));
    }

    @PostMapping()
    public ResponseEntity<UnitDto> create(@PathVariable UUID stationId, @Valid  @RequestBody UnitDto dto) {
        return ResponseEntity.ok(unitService.create(stationId, dto));
    }

    @PutMapping()
    public ResponseEntity<UnitDto> update(@PathVariable UUID stationId, @Valid @RequestBody UnitDto dto) {
        if (stationId == null || dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.UNIT);
        return ResponseEntity.ok(unitService.update(dto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@PathVariable UUID stationId, @Valid @RequestBody UnitDto dto) {
        if (stationId == null || dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.UNIT);
        unitService.delete(dto);
        return ResponseEntity.noContent().build();
    }

}
