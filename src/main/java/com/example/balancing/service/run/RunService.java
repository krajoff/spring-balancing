package com.example.balancing.service.run;

import com.example.balancing.entity.run.Run;

import java.util.List;

public interface RunService {
    Run getRunById(Long id);

    Run getRunByWeightId(Long id);

    List<Run> getRunsByUnitId(Long id);

    Run createRun(Run run);

    Run updateRun(Long id, Run run);

    void deleteRunById(Long id);


}
