package com.example.balancing.service.record;

import com.example.balancing.dto.RecordDto;

import java.util.List;
import java.util.UUID;

public interface RecordService {

    List<RecordDto> getByRunId(UUID id);

    List<RecordDto> getByUnit(UUID id);

    RecordDto create(UUID runId, RecordDto dto);

    RecordDto update(RecordDto dto);

    void delete(RecordDto dto);

}
