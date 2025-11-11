package com.example.balancing.utils;

import com.example.balancing.dto.mode.ModeDto;
import com.example.balancing.entity.mode.Mode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Mode и ModeDto:
 * ModeDto содержит id и name.
 */
@Mapper(componentModel = "spring")
public abstract class ModeMapper {

    public abstract ModeDto entityToDto(Mode mode);

    @Mapping(target = "records", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Mode dtoToEntity(ModeDto modeDto);

}
