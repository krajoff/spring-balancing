package com.example.balancing.utils;

import com.example.balancing.dtos.point.*;
import com.example.balancing.models.point.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Point, PointDto и
 * SimplifiedPointDto
 */
@Mapper(componentModel = "spring", uses = RecordMapper.class)
public abstract class PointMapper {

    /**
     * Преобразует сущность Point в PointDto.
     *
     * @param point сущность Point
     * @return объект PointDto
     */
    @Mapping(source = "records", target = "records",
            qualifiedByName = "mapRecordsToRecordsDto")
    public abstract PointDto pointToPlateDto(Point point);

    /**
     * Преобразует сущность PointDto to Point.
     *
     * @param pointDto сущность PointDto
     * @return объект Point
     */
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Point pointDtoToPoint(PointDto pointDto);


    /**
     * Преобразует сущность Point в PointDto.
     *
     * @param point сущность Point
     * @return объект PointDto
     */
    public abstract SimplifiedPointDto
    pointToSimplifiedPlateDto(Point point);

    /**
     * Преобразует сущность SimplifiedPointDto to Point.
     *
     * @param pointDto сущность SimplifiedPointDto
     * @return объект Point
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Point
    simplifiedpointDtoToPoint(SimplifiedPointDto pointDto);

}
