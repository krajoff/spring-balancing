package com.example.balancing.utils;

import com.example.balancing.dtos.plane.PlaneDto;
import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.dtos.run.RunDto;
import com.example.balancing.dtos.weight.WeightDto;
import com.example.balancing.models.weight.Weight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями Weight и WeightDto
 */
@Mapper(componentModel = "spring",
        uses = {RunMapper.class, RecordMapper.class, PlaneMapper.class} )
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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Weight weightDtoToWeight(WeightDto weightDto);

}
