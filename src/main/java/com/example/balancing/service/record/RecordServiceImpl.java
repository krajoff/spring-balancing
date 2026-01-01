package com.example.balancing.service.record;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.entity.Record;
import com.example.balancing.entity.run.Run;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.repository.RunRepository;
import com.example.balancing.transformer.RecordMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;
    private RecordMapper recordMapper;
    private RunRepository runRepository;

    @Transactional
    @Override
    public List<RecordDto> getByRunId(UUID runId) {
        return recordRepository.findByRunId(runId)
                .stream()
                .map(recordMapper::entityToDto)
                .toList();
    }

    @Transactional
    @Override
    public RecordDto create(UUID runId, RecordDto dto) {
        Run run = runRepository.findById(runId).orElseThrow(() -> new NotFoundElementException(EntityTypeException.RUN));
        Record record = recordMapper.dtoToEntity(dto);
        record.setRun(run);
        return recordMapper.entityToDto(recordRepository.save(record));
    }

    @Transactional
    @Override
    public RecordDto update(RecordDto dto) {
        if (dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RECORD);
        Record existing = recordRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.RECORD));
        if (dto.getPointName() != null) existing.setPointName(dto.getPointName());
        if (dto.getModeName() != null) existing.setModeName(dto.getModeName());
        if (dto.getMagVibration() != null) existing.setMagVibration(dto.getMagVibration());
        if (dto.getPhaseVibration() != null) existing.setPhaseVibration(dto.getPhaseVibration());
        if (dto.getIsUsed() != null) existing.setIsUsed(dto.getIsUsed());
        if (dto.getMagSensitivity() != null) existing.setMagSensitivity(dto.getMagSensitivity());
        if (dto.getPhaseSensitivity() != null) existing.setPhaseSensitivity(dto.getPhaseSensitivity());
        return recordMapper.entityToDto(recordRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(RecordDto dto) {
        if (dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RECORD);
        recordRepository.deleteById(dto.getId());
    }
}
