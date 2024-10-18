package com.example.balancing.utils;

import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.record.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования между сущностями Record и RecordDto:
 * RecordDto содержит все поля кроме weight и полей
 * создания-обновления-версии.
 */
@Mapper(componentModel = "spring", uses = {ModeMapper.class, PointMapper.class})
public abstract class RecordMapper {

    /**
     * Преобразует сущность Record в RecordDto.
     *
     * @param record сущность Record
     * @return объект RecordDto
     */
    public abstract RecordDto recordToRecordDto(Record record);

    /**
     * Преобразует сущность RecordDto to Record.
     *
     * @param recordDto сущность RecordDto
     * @return объект Record
     */
    @Mapping(target = "weight", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Record recordDtoToRecord(RecordDto recordDto);

    /**
     * Преобразует список Record в список RecordDto.
     *
     * @param records список Record
     * @return список RecordDto
     */
    @Named("mapRecordsToRecordsDto")
    public List<RecordDto> mapRecordsToRecordsDto(List<Record> records) {
        return Optional.ofNullable(records)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::recordToRecordDto)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует список RecordDto в список Record.
     *
     * @param records список RecordDto
     * @return список Record
     */
    @Named("mapRecordsDtoToRecords")
    public List<Record> mapRecordsDtoToRecords(List<RecordDto> records) {
        return Optional.ofNullable(records)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::recordDtoToRecord)
                .collect(Collectors.toList());
    }


}
