package com.example.balancing.services.run;

import com.example.balancing.models.run.Run;

import java.util.List;

public interface RunService {
    Run getRunById(Long id);

    Run getRunByWeightId(Long id);

    List<Run> getRunsByUnitId(Long id);

    Run createRun(Run run);

    Run updateRun(Long id, Run run);

    void deleteRunById(Long id);


}
