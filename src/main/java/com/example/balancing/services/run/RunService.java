package com.example.balancing.services.run;

import com.example.balancing.models.run.Run;

public interface RunService {
    Run getRunById(Long id);

    Run createRun(Run run);

    Run updateRun(Long id, Run run);

    void deleteRunById(Long id);

    Run findByWeightId(Long id);
}
