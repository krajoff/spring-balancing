package com.example.balancing.utils;

import com.example.balancing.dtos.station.SimplifiedStationDto;
import com.example.balancing.dtos.station.StationDto;
import com.example.balancing.models.station.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Маппер для преобразования между сущностями Station, StationDto и
 * SimplifiedStationDto
 */
@Mapper(componentModel = "spring")
public abstract class StationMapper {

    /**
     * Преобразование сущности Station в StationDto
     *
     * @param station сущность Station
     * @return StationDto
     */
    public abstract StationDto stationToStationDto(Station station);

    /**
     * Преобразование сущности StationDto в Station
     *
     * @param stationDto сущность StationDto
     * @return Station
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Station stationDtoToStation(StationDto stationDto);

    /**
     * Преобразование сущности Station в SimplifiedStationDto
     *
     * @param station сущность Station
     * @return SimplifiedStationDto
     */
    public abstract SimplifiedStationDto
    stationToSimplifiedStationDto(Station station);

    /**
     * Преобразование сущности StationDto в Station
     *
     * @param stationDto сущность StationDto
     * @return Station
     */
    @Mapping(target = "units", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Station
    simplifiedstationDtoToStation(SimplifiedStationDto stationDto);

}
