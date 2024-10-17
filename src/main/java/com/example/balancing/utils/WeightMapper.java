package com.example.balancing.utils;

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
    @Mapping(source = "records", target = "records",
            qualifiedByName = "mapRecordsToRecordsDto")
    @Mapping(source = "run.number", target = "run")
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
    @Mapping(source = "run", target = "run", qualifiedByName = "getRunByNumber")
    public abstract Weight weightDtoToWeight(WeightDto weightDto);

}
