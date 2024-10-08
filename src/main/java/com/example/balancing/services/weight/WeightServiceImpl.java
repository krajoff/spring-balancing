package com.example.balancing.services.weight;

import com.example.balancing.exception.WeightNotFoundException;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.weight.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
public class WeightServiceImpl implements WeightService {

    @Autowired
    private WeightRepository weightRepository;

    public Weight getWeightById(Long id) {
        return weightRepository.findById(id)
                .orElseThrow(WeightNotFoundException::new);
    }

    public Weight createWeight(Weight weight) {
        return weightRepository.save(weight);
    }

    public Weight updateWeight(Long id, Weight weight) {
        Weight existingWeight = getWeightById(id);
        existingWeight.setPlane(weight.getPlane());
        existingWeight.setRun(weight.getRun());
        existingWeight.setReference(weight.getReference());
        existingWeight.setMagWeight(weight.getMagWeight());
        existingWeight.setPhaseWeight(weight.getPhaseWeight());
        existingWeight.setSystemInformation(weight.getSystemInformation());
        return weightRepository.save(existingWeight);
    }

    public void deleteWeight(Long id) {
        weightRepository.deleteById(id);
    }




    public boolean fixCycles(Unit unit) {
        return unit.getWeights().stream().anyMatch(this::isValidReference);
    }

    public Weight getValidReference(Weight weight) {
        if (isValidReference(weight)) {
            return weight;
        }
        weight.setReference(-1L);
        return weight;
    }

    private boolean isValidReference(Weight weight) {
        Set<Integer> visited = new HashSet<>();
        var weights = getWeightsByUnit(weight.getUnit());
        while (weight.getReference() != -1) {
            if (visited.contains(weight.getNumberRun())) {
                return false;
            }
            visited.add(weight.getNumberRun());
            long finalReference = weight.getReference();
            Optional<Weight> weightNext = weights.stream()
                    .filter(w -> w.getNumberRun() == finalReference).findFirst();
            if (weightNext.isEmpty()) {
                return false;
            }
            weight = weightNext.get();
        }
        return true;
    }
}
