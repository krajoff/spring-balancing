package com.example.balancing.utils;

import com.example.balancing.dtos.point.PointDto;
import com.example.balancing.dtos.point.SimplifiedPointDto;
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
    @Mapping(source = "records", target = "records")
    public abstract PointDto pointToPlateDto(Point point);

    /**
     * Преобразует сущность PointDto to Point.
     *
     * @param pointDto сущность PointDto
     * @return объект Point
     */
    @Mapping(target = "unit", ignore = true)
    @Mapping(source = "records", target = "records")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Point pointDtoToPoint(PointDto pointDto);


    /**
     * Преобразует сущность Point в SimplifiedPointDto.
     *
     * @param point сущность Point
     * @return объект PointDto
     */
    public abstract SimplifiedPointDto pointToSimplifiedPlateDto(Point point);

    /**
     * Преобразует сущность SimplifiedPointDto to Point.
     *
     * @param pointDto сущность SimplifiedPointDto
     * @return объект Point
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Point simplifiedpointDtoToPoint(SimplifiedPointDto pointDto);

//    /**
//     * Преобразует список Record в список RecordDto.
//     *
//     * @param records список Record
//     * @return список RecordDto
//     */
//    @Named("recordsToRecordsDto")
//    public List<RecordDto> recordsToRecordsDto(List<Record> records) {
//        return Optional.ofNullable(records)
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(recordMapper::recordToRecordDto)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Преобразует список RecordDto в список Record.
//     *
//     * @param records список RecordDto
//     * @return список Record
//     */
//    @Named("recordsDtoToRecords")
//    public List<Record> recordsDtoToRecords(List<RecordDto> records) {
//        return Optional.ofNullable(records)
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(recordMapper::recordDtoToRecord)
//                .collect(Collectors.toList());
//    }


}
