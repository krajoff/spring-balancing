package com.example.balancing.utils;

import com.example.balancing.dtos.plane.PlaneDto;
import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.dtos.weight.WeightDto;
import com.example.balancing.models.weight.Weight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Weight и WeightDto
 */
@Mapper(componentModel = "spring", uses = {PlaneDto.class, RecordDto.class} )
public abstract class WeightMapper {

    /**
     * Преобразование сущности Weight в WeightDto
     *
     * @param weight сущность Weight
     * @return WeightDto
     */
    public abstract WeightDto weightToWeightDto(Weight weight);

    /**
     * Преобразование сущности WeightDto в Weight
     *
     * @param weightDto сущность WeightDto
     * @return Weight
     */
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "weights", ignore = true)
    @Mapping(target = "station", ignore = true)
    public abstract Weight weightDtoToWeight(WeightDto weightDto);

}
