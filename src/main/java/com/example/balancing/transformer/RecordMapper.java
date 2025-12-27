package com.example.balancing.transformer;

import com.example.balancing.dto.record.RecordDto;
import com.example.balancing.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RecordMapper {

    @Mapping(target = "complexVibration", ignore = true)
    @Mapping(target = "complexSensitivity", ignore = true)
    public abstract RecordDto recordToRecordDto(Record record);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "run", ignore = true)
    public abstract Record recordDtoToRecord(RecordDto recordDto);

}
