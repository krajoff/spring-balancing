package com.example.balancing.services.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.weight.Weight;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CycleAndWrongReferenceCheckerService {

    @Autowired
    private WeightService weightService;

    @Transactional
    public boolean hasCycles(Unit unit) {
        for (Weight weight : weightService.getWeightByUnit(unit)) {
            if (isCyclic(weight, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCyclic(Weight weight, Set<Long> visited) {
        if (weight.getReference() == -1) {
            return false;
        }
        if (visited.contains(weight.getInsideId())) {
            return true;
        }
        visited.add(record.getId());
        Optional<Record> nextRecord = recordRepository.findById(record.getReference());
        return nextRecord.isPresent() && isCyclic(nextRecord.get(), visited);
    }
}
