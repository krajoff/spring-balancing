package com.example.balancing.controller.api;

import com.example.balancing.dto.RunDto;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.service.run.RunService;
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
@RequestMapping("/api/stations/{stationId}/units/{unitId}/runs")
public class RunController {

    private final RunService runService;

    @GetMapping
    public ResponseEntity<List<RunDto>> getByUnit(@PathVariable UUID stationId, @PathVariable UUID unitId) {
        log.info("Get all runs for unit {} in station {}", unitId, stationId);
        return ResponseEntity.ok(runService.getByUnitId(unitId));
    }

    @PostMapping
    public ResponseEntity<RunDto> create(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                         @Valid @RequestBody RunDto dto) {
        log.info("Create run for unit {} in station {}", unitId, stationId);
        return ResponseEntity.ok(runService.create(unitId, dto));
    }

    @PutMapping
    public ResponseEntity<RunDto> update(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                         @Valid @RequestBody RunDto dto) {
        if (unitId == null || dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RUN);
        log.info("Update run {} for unit {} in station {}", dto.getId(), unitId, stationId);
        return ResponseEntity.ok(runService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                       @Valid @RequestBody RunDto dto) {
        if (unitId == null || dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RUN);
        log.info("Delete run {} for unit {} in station {}", dto.getId(), unitId, stationId);
        runService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}

