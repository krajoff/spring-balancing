package com.example.balancing.service.unit;

import com.example.balancing.dto.UnitDto;

import java.util.List;
import java.util.UUID;

public interface UnitService {

    List<UnitDto> getByStation(UUID id);

    UnitDto create(UUID stationId, UnitDto dto);

    UnitDto update(UnitDto dto);

    void delete(UnitDto dto);

}
