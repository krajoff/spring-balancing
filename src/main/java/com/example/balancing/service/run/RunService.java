package com.example.balancing.service.run;

import com.example.balancing.dto.RunDto;

import java.util.List;
import java.util.UUID;

public interface RunService {

    RunDto create(UUID unitId, RunDto dto);

    List<RunDto> getByUnitId(UUID unitId);

    RunDto update(RunDto dto);

    void delete(RunDto dto);

}
