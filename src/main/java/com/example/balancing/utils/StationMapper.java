package com.example.balancing.utils;

import com.example.balancing.dtos.station.StationDto;
import com.example.balancing.models.station.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Station и StationDto:
 */
@Mapper(componentModel = "spring", uses = UnitMapper.class)
public abstract class StationMapper {

    /**
     *
     * @param station
     * @return
     */
    @Mapping(source = "units", target = "units")
    public abstract StationDto stationToStationDto(Station station);

    /**
     *
     * @param stationDto
     * @return
     */
    @Mapping(target = )
    @Mapping(source = "units", target = "units.id")
    public abstract Station stationDtoToStation(StationDto stationDto);
}
