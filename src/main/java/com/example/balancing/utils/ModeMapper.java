package com.example.balancing.utils;

import com.example.balancing.dtos.mode.ModeDto;
import com.example.balancing.models.mode.Mode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Mode и ModeDto:
 * ModeDto содержит id и name.
 */
@Mapper(componentModel = "spring")
public abstract class ModeMapper {

    /**
     * Преобразует сущность Mode в ModeDto.
     *
     * @param mode сущность Mode
     * @return объект ModeDto
     */
    public abstract ModeDto modeToModeDto(Mode mode);

    /**
     * Преобразует сущность ModeDto to Mode.
     *
     * @param modeDto сущность ModeDto
     * @return объект Mode
     */
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Mode modeDtoToMode(ModeDto modeDto);

}
