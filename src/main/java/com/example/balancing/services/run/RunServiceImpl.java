package com.example.balancing.services.run;

import com.example.balancing.exception.RunNotFoundException;
import com.example.balancing.models.run.Run;
import com.example.balancing.repositories.run.RunRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RunServiceImpl implements RunService {

    @Autowired
    RunRepository runRepository;

    @Override
    public Run getRunById(Long id) {
        return runRepository.findById(id).orElseThrow(RunNotFoundException::new);
    }

    @Override
    public Run createRun(Run run) {
        List<Run> runs = runRepository.findByUnitId(run.getUnit().getId());
        int number = runs == null ? 0 : runs.size();
        run.setNumber(number);

        Run run = runRepository.findById(run.getReferenceRun().getId()).or(null);
        if (!isValidReferencePlane(run) || number == 0)
            run.setReferenceRun(null);
        return runRepository.save(run);
    }

    @Override
    public Run updateRun(Long id, Run run) {
        Run existingRun = getRunById(id);
        existingRun.setPlane(run.getPlane());
        existingRun.setWeight(run.getWeight());
        existingRun.setReferenceRun(run.getReferenceRun());
        return createRun(existingRun);
    }

    @Override
    @Transactional
    public void deleteRun(Long id) {
        runRepository.deleteById(id);
        runRepository.setNullReference(id);
    }

    private  isValidReferencePlane(Run run) {
        return runRepository.findById(run.getReferenceRun().getId()).or(null);
    }

}
