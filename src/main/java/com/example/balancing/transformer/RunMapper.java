package com.example.balancing.transformer;

import com.example.balancing.dto.run.RunDto;
import com.example.balancing.entity.run.Run;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RunMapper {

    public abstract RunDto runToRunDto(Run run);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "records", ignore = true)
    public abstract Run runDtoToRun(RunDto runDto);

}
