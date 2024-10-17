package com.example.balancing.utils;

import com.example.balancing.dtos.run.SimplifiedRunDto;
import com.example.balancing.dtos.run.RunDto;
import com.example.balancing.models.run.Run;
import com.example.balancing.services.run.RunService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {PlaneMapper.class, WeightMapper.class})
public abstract class RunMapper {

    @Autowired
    private final RunService runService;

    /**
     * Преобразование сущности Run в RunDto
     *
     * @param run сущность Run
     * @return RunDto
     */
    public abstract RunDto runToRunDto(Run run);

    /**
     * Преобразование сущности RunDto в Run
     *
     * @param runDto сущность RunDto
     * @return Run
     */
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Run runDtoToRun(RunDto runDto);

    /**
     * Преобразование сущности Run в SimplifiedRunDto
     *
     * @param run сущность Run
     * @return SimplifiedRunDto
     */
    public abstract SimplifiedRunDto
    runToSimplifiedRunDto(Run run);

    /**
     * Преобразование сущности RunDto в Run
     *
     * @param runDto сущность RunDto
     * @return Run
     */
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "plane", ignore = true)
    @Mapping(target = "weight", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    public abstract Run simplifiedRunDtoToRun(SimplifiedRunDto runDto);

    public Run getRunByWeightId(Long id) {
        return runService.getRunByWeightId(id);
    }


}
