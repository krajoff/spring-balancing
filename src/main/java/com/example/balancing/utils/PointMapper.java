package com.example.balancing.utils;

import com.example.balancing.dtos.point.*;
import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.point.Point;
import com.example.balancing.models.record.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между сущностями Point, PointDto и
 * SimplifiedPointDto
 */
@Mapper(componentModel = "spring", uses = RecordMapper.class)
public abstract class PointMapper {

    @Autowired
    private RecordMapper recordMapper;

    /**
     * Преобразует сущность Point в PointDto.
     *
     * @param point сущность Point
     * @return объект PointDto
     */
    @Mapping(source = "records", target = "records",
            qualifiedByName = "recordsToRecordsDto")
    public abstract PointDto pointToPlateDto(Point point);

    /**
     * Преобразует сущность PointDto to Point.
     *
     * @param pointDto сущность PointDto
     * @return объект Point
     */
    @Mapping(target = "unit", ignore = true)
    @Mapping(source = "records", target = "records",
            qualifiedByName = "recordsDtoToRecords")
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

    /**
     * Преобразует список Record в список RecordDto.
     *
     * @param records список Record
     * @return список RecordDto
     */
    @Named("recordsToRecordsDto")
    public List<RecordDto> recordsToRecordsDto(List<Record> records) {
        return Optional.ofNullable(records)
                .orElse(Collections.emptyList())
                .stream()
                .map(recordMapper::recordToRecordDto)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует список RecordDto в список Record.
     *
     * @param records список RecordDto
     * @return список Record
     */
    @Named("recordsDtoToRecords")
    public List<Record> recordsDtoToRecords(List<RecordDto> records) {
        return Optional.ofNullable(records)
                .orElse(Collections.emptyList())
                .stream()
                .map(recordMapper::recordDtoToRecord)
                .collect(Collectors.toList());
    }


}
