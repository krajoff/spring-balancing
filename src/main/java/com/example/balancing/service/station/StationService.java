package com.example.balancing.service.station;

import com.example.balancing.dto.StationDto;

import java.util.List;

public interface StationService {

    StationDto create(StationDto dto);

    List<StationDto> getAllByUser();

    StationDto update(StationDto dto);

    void delete(StationDto dto);

}
