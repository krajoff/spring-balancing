package com.example.balancing.controller.api;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.service.record.RecordService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stations/{stationId}/units/{unitId}/runs/{runId}/records")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordDto>> getAllByRunId(@PathVariable UUID stationId,
                                                         @PathVariable UUID unitId,
                                                         @NonNull @PathVariable UUID runId) {
        log.info("Get records for run {}, unit {}, station {}", runId, unitId, stationId);
        return ResponseEntity.ok(recordService.getByRunId(runId));
    }

    @PostMapping
    public ResponseEntity<RecordDto> create(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                            @PathVariable UUID runId, @Valid @RequestBody RecordDto dto) {
        log.info("Create record for run {}, unit {}, station {}", runId, unitId, stationId);
        return ResponseEntity.ok(recordService.create(runId, dto));
    }

    @PutMapping
    public ResponseEntity<RecordDto> update(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                            @PathVariable UUID runId, @Valid @RequestBody RecordDto dto) {
        if (dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RECORD);
        log.info("Update record {} for run {}, unit {}, station {}", dto.getId(), runId, unitId, stationId);
        return ResponseEntity.ok(recordService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable UUID stationId, @PathVariable UUID unitId,
                                       @PathVariable UUID runId, @Valid @RequestBody RecordDto dto) {
        if (dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RECORD);
        log.info("Delete record {} for run {}, unit {}, station {}", dto.getId(), runId, unitId, stationId);
        recordService.delete(dto);
        return ResponseEntity.noContent().build();
    }

}
