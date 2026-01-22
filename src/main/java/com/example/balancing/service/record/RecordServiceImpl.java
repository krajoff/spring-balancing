package com.example.balancing.service.record;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.entity.Record;
import com.example.balancing.entity.run.Run;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.repository.RunRepository;
import com.example.balancing.service.user.UserService;
import com.example.balancing.transformer.RecordMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final RunRepository runRepository;
    private final UserService userService;

    @Transactional
    @Override
    public List<RecordDto> getByRunId(UUID runId) {
        User user = userService.getCurrentUser();
        return recordRepository.findByRunIdAndUserId(runId, user.getId())
                .stream()
                .map(recordMapper::entityToDto)
                .toList();
    }

    @Override
    public List<RecordDto> getByUnit(UUID unitId) {
        User user = userService.getCurrentUser();
        return recordRepository.findByUnitIdAndUserId(unitId, user.getId())
                .stream()
                .map(recordMapper::entityToDto)
                .toList();
    }

    @Transactional
    @Override
    public RecordDto create(UUID runId, RecordDto dto) {
        User user = userService.getCurrentUser();
        Run run = runRepository.findByIdAndUserId(runId, user.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.RUN));
        Record record = recordMapper.dtoToEntity(dto);
        record.setRun(run);
        return recordMapper.entityToDto(recordRepository.save(record));
    }

    @Transactional
    @Override
    public RecordDto update(RecordDto dto) {
        User user = userService.getCurrentUser();
        if (dto.getId() == null) throw new IllegalArgumentException(EntityTypeException.RECORD);
        Record existing = recordRepository.findByIdAndUserId(dto.getId(), user.getId())
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
        User user = userService.getCurrentUser();
        recordRepository.deleteByIdAndUserId(dto.getId(), user.getId());
    }
}
