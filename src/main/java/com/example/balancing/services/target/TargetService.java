package com.example.balancing.services.target;

import com.example.balancing.models.target.Target;
import com.example.balancing.models.unit.Unit;

import java.util.List;

public interface TargetService {
    List<Target> getAllTargets();

    Target getTargetById(Long id);

    List<Target> getTargetsByUnit(Unit unit);

    Target createTarget(Target target);

    Target updateTarget(Long id, Target target);

    void deleteTarget(Long id);
}
