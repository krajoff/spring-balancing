package com.example.balancing.services.weight;

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
                .orElseThrow(() -> new RuntimeException("Weight not found"));
    }

    public Weight createWeight(Weight weight) {
        getValidReference(weight);
        calculateWeight(weight);
        return weightRepository.save(weight);
    }

    public Weight updateWeight(Long id, Weight weight) {
        Weight existingWeight = getWeightById(id);
        existingWeight.setMagRefWeight(weight.getMagRefWeight());
        existingWeight.setPhaseRefWeight(weight.getPhaseRefWeight());
        existingWeight.setPlane(weight.getPlane());
        existingWeight.setReference(weight.getReference());
        getValidReference(existingWeight);
        calculateWeight(existingWeight);
        return weightRepository.save(existingWeight);
    }

    public void deleteWeight(Long id) {
        weightRepository.deleteById(id);
    }

    public List<Weight> getWeightsByUnit(Unit unit) {
        if (weightRepository.findByUnit(unit).isPresent())
            return weightRepository.findByUnit(unit).get();
        return null;
    }

    public Weight calculateWeight(Weight weight) {
        List<Weight> weights = getWeightsByUnit(weight.getUnit());
        long reference = weight.getReference();
        Complex complexWeight = weight.getComplexRefWeight();
        Optional<Weight> tempWeight;
        while (reference != -1) {
            long finalReference = reference;
            tempWeight = weights
                    .stream()
                    .filter(w -> w.getNumberRun()
                            == finalReference).findFirst();
            if (tempWeight.isPresent()) {
                complexWeight = complexWeight
                        .plus(tempWeight.get().getComplexRefWeight());
                reference = tempWeight.get().getReference();
            } else {
                reference = -1;
                weight.setSystemInformation("Invalid reference in a chain");
            }
        }
        weight.setComplexWeight(complexWeight);
        weight.setMagWeight(complexWeight.abs());
        weight.setPhaseWeight(complexWeight.phase());
        return weight;
    }


    public boolean fixCycles(Unit unit) {
        return unit.getWeights().stream().anyMatch(this::isValidReference);
    }

    public Weight getValidReference(Weight weight) {
        if (isValidReference(weight)) {
            return weight;
        }
        weight.setReference(-1);
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
