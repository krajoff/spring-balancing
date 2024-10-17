package com.example.balancing.services.run;

import com.example.balancing.exception.run.RunNotFoundException;
import com.example.balancing.models.run.Run;
import com.example.balancing.repositories.run.RunRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RunServiceImpl implements RunService {

    @Autowired
    RunRepository runRepository;

    @Override
    public Run getRunById(Long id) {
        return runRepository.findById(id)
                .orElseThrow(RunNotFoundException::new);
    }

    @Override
    public Run getRunByWeightId(Long id) {
        return runRepository.findByWeightId(id)
                .orElseThrow(RunNotFoundException::new);
    }

    @Override
    public List<Run> getRunsByUnitId(Long id) {
        return runRepository.findByUnitId(id)
                .orElseThrow(RunNotFoundException::new);
    }

    @Override
    public Run createRun(Run run) {
        List<Run> runs = getRunsByUnitId(run.getUnit().getId());
        int number = runs == null ? 0 : runs.size();
        run.setNumber(number);
        if (!isValidReferenceRun(run) || number == 0)
            run.setReferenceRunId(null);
        return runRepository.save(run);
    }

    @Override
    public Run updateRun(Long id, Run run) {
        Run existingRun = getRunById(id);
        existingRun.setPlane(run.getPlane());
        existingRun.setWeight(run.getWeight());
        if (isValidReferenceRun(run))
            existingRun.setReferenceRunId(run.getReferenceRunId());
        return createRun(existingRun);
    }

    @Override
    @Transactional
    public void deleteRunById(Long id) {
        Run run = getRunById(id);
        runRepository.deleteById(id);
        runRepository.setNullReference(id);
        runRepository.alterNumberRun(run.getNumber());
    }


    private boolean isValidReferenceRun(Run run) {
        return runRepository.findById(run.getReferenceRunId()).isPresent();
    }

    private boolean isCyclicReference(Run run) {
        // Получаем пуски данного агрегата
        List<Run> runs = getRunsByUnitId(run.getUnit().getId());
        Set<Long> visitedRunIds = new HashSet<>();
        Long referenceRunId = run.getReferenceRunId();
        visitedRunIds.add(run.getId());

        // Проверка на null для начального пуска
        while (referenceRunId != null) {
            // Проверка на наличие текущего пуска в множестве посещённых
            if (visitedRunIds.contains(referenceRunId))
                return true;

            // Добавляем текущий id пуска в посещённые
            visitedRunIds.add(referenceRunId);

            // Находим текущий пуск по его id
            Long finalReferenceRunId = referenceRunId;
            Optional<Run> currentRun = runs.stream()
                    .filter(r -> r.getId().equals(finalReferenceRunId))
                    .findFirst();

            // Если текущий пуск не найден, выходим из цикла
            if (currentRun.isEmpty())
                break;

            // Обновляем referenceRunId для следующей итерации
            referenceRunId = currentRun.get().getReferenceRunId();
        }
        return false;
    }

}
