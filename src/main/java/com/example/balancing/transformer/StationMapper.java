package com.example.balancing.transformer;

import com.example.balancing.dto.StationDto;
import com.example.balancing.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public abstract class StationMapper {

    public abstract StationDto stationToStationDto(Station station);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(source = "units", target = "units")
    public abstract Station stationDtoToStation(StationDto stationDto);


}
