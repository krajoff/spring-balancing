package com.example.balancing.services.target;

import com.example.balancing.models.target.Target;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TargetServiceImpl implements TargetService {

    @Autowired
    private TargetRepository targetRepository;

    public List<Target> getAllTargets() {
        return targetRepository.findAll();
    }

    public Target getTargetById(Long id) {
        return targetRepository.findById(id).orElseThrow(() -> new RuntimeException("Target not found"));
    }

    public List<Target> getTargetsByUnit(Unit unit) {
        return targetRepository.findByUnit(unit);
    }

    public Target createTarget(Target target) {
        return targetRepository.save(target);
    }

    public Target updateTarget(Long id, Target target) {
        Target existingTarget = getTargetById(id);
        existingTarget.setMode(target.getMode());
        existingTarget.setMagWeight(target.getMagWeight());
        existingTarget.setPhaseWeight(target.getPhaseWeight());
        return targetRepository.save(existingTarget);
    }

    public void deleteTarget(Long id) {
        targetRepository.deleteById(id);
    }
}
