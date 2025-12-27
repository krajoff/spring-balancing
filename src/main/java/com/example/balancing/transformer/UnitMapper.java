package com.example.balancing.transformer;

import com.example.balancing.dto.unit.UnitDto;
import com.example.balancing.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UnitMapper {

    public abstract UnitDto unitToUnitDto(Unit unit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "runs", ignore = true)
    @Mapping(target = "station", ignore = true)
    public abstract Unit unitDtoToUnit(UnitDto unitDto);

}
