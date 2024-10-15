package com.example.balancing.utils;

import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.record.Record;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Record и RecordDto:
 * RecordDto содержит все поля кроме weight и полей
 * создания-обновления-версии.
 */
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
}
