package com.example.balancing.utils;

import com.example.balancing.dtos.plane.PlaneDto;
import com.example.balancing.models.plane.Plane;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Plane и PlaneDto:
 * PlaneDto содержит id и number.
 */
@Mapper(componentModel = "spring")
public abstract class PlaneMapper {

    /**
     * Преобразует сущность Plane в PlaneDto.
     *
     * @param plane сущность Plane
     * @return объект PlaneDto
     */
    public abstract PlaneDto planeToPlateDto(Plane plane);

    /**
     * Преобразует сущность PlaneDto to Plane.
     *
     * @param planeDto сущность PlaneDto
     * @return объект Plane
     */
    @Mapping(target = "runWeightMap", ignore = true)
    @Mapping(target = "weights", ignore = true)
    @Mapping(target = "targetWeights", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Plane planeDtoToPlane(PlaneDto planeDto);

}
