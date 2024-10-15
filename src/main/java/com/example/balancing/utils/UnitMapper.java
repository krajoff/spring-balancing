package com.example.balancing.utils;

import com.example.balancing.dtos.unit.SimplifiedUnitDto;
import com.example.balancing.dtos.unit.UnitDto;
import com.example.balancing.models.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Unit, UnitDto
 * и SimplifiedUnitDto:
 */
@Mapper(componentModel = "spring", uses = WeightMapper.class)
public abstract class UnitMapper {

    /**
     * Преобразование сущности Unit в UnitDto
     *
     * @param unit сущность Unit
     * @return UnitDto
     */
    public abstract UnitDto unitToUnitDto(Unit unit);

    /**
     * Преобразование сущности UnitDto в Unit
     *
     * @param unitDto сущность UnitDto
     * @return Unit
     */
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "station", ignore = true)
    public abstract Unit unitDtoToUnit(UnitDto unitDto);

    /**
     * Преобразование сущности Unit в SimplifiedUnitDto
     *
     * @param unit сущность Unit
     * @return SimplifiedUnitDto
     */
    public abstract SimplifiedUnitDto unitTosimplifiedUnitDto(Unit unit);

    /**
     * Преобразование сущности SimplifiedUnitDto в Unit
     *
     * @param unitDto сущность SimplifiedUnitDto
     * @return Unit
     */
    @Mapping(target = "runs", ignore = true)
    @Mapping(target = "planes", ignore = true)
    @Mapping(target = "modes", ignore = true)
    @Mapping(target = "points", ignore = true)
    @Mapping(target = "station", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Unit simplifiedUnitDtoToUnit(SimplifiedUnitDto unitDto);
}
