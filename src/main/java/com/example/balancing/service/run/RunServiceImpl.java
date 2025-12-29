package com.example.balancing.service.run;

import com.example.balancing.dto.RunDto;
import com.example.balancing.entity.Unit;
import com.example.balancing.entity.run.Run;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.RunRepository;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.transformer.RunMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;
    private final RunMapper runMapper;
    private final UnitRepository unitRepository;

    @Transactional
    @Override
    public RunDto create(UUID unitId, RunDto dto) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.UNIT));

        Run run = runMapper.dtoToEntity(dto);
        run.setUnit(unit);

        UUID referenceRunId = runRepository
                .findByUnitIdAndRunNumber(unitId, dto.getRunNumber())
                .map(Run::getId)
                .orElse(null);
        run.setReferenceRunId(referenceRunId);

        return runMapper.entityToDto(runRepository.save(run));
    }

    @Transactional
    @Override
    public List<RunDto> getByUnitId(UUID unitId) {
        List<Run> runs = runRepository.findByUnitId(unitId)
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.UNIT));
        return runs.stream().map(runMapper::entityToDto).toList();
    }

    @Transactional
    @Override
    public RunDto update(RunDto dto) {
        Run existingRun = runRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.RUN));
        log.info("Run to update: {}", dto);
        if (dto.getRunNumber() != null) existingRun.setRunNumber(dto.getRunNumber());
        if (dto.getRunParameters() != null) existingRun.setRunsParameters(dto.getRunParameters());
        if (isCyclicReference(existingRun)) throw new IllegalArgumentException("Cyclic reference detected for Run");

        return runMapper.entityToDto(runRepository.save(existingRun));
    }

    @Transactional
    @Override
    public void delete(RunDto dto) {
        Run run = runRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.RUN));

        List<Run> dependentRuns = runRepository.findByUnitIdAndReferenceRunId(
                        run.getUnit().getId(),
                        run.getId())
                .orElse(List.of());

        dependentRuns.forEach(r -> r.setReferenceRunId(null));
        runRepository.saveAll(dependentRuns);
        runRepository.delete(run);
    }

    private boolean isCyclicReference(Run run) {
        List<Run> runs = runRepository.findByUnitId(run.getUnit().getId()).orElse(Collections.emptyList());

        Set<UUID> visited = new HashSet<>();
        UUID refId = run.getReferenceRunId();
        visited.add(run.getId());

        while (refId != null) {
            if (!visited.add(refId)) return true;

            UUID finalRefId = refId;
            Optional<Run> next = runs.stream()
                    .filter(r -> r.getId().equals(finalRefId))
                    .findFirst();

            if (next.isEmpty()) break;

            refId = next.get().getReferenceRunId();
        }
        return false;
    }

}
