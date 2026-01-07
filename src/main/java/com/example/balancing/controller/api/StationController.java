package com.example.balancing.controller.api;

import com.example.balancing.dto.StationDto;
import com.example.balancing.service.station.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/station")
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<List<StationDto>> getAll() {
        return ResponseEntity.ok(stationService.getAllByUser());
    }

    @PostMapping
    public ResponseEntity<StationDto> create(@Valid @RequestBody StationDto dto) {
        return ResponseEntity.ok(stationService.create(dto));
    }

    @PutMapping()
    public ResponseEntity<StationDto> update(@Valid @RequestBody StationDto dto) {
        return ResponseEntity.ok(stationService.update(dto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@Valid @RequestBody StationDto dto) {
        stationService.delete(dto);
        return ResponseEntity.noContent().build();
    }

}

