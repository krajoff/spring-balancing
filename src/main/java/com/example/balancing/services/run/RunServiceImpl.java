package com.example.balancing.services.run;

import com.example.balancing.exception.RunNotFoundException;
import com.example.balancing.models.run.Run;
import com.example.balancing.repositories.run.RunRepository;
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
        return runRepository.save(run);
    }

    @Override
    public Run updateRun(Long id, Run run) {
        Run existingRun = getRunById(id);
        existingRun.setPlanes();
        existingRun.setNumber(run.getNumber());
        return createRun(existingRun);
    }

    @Override
    public void deleteRun(Long id) {
        runRepository.deleteById(id);
    }
}
