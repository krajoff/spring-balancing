package com.example.balancing.services.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repository.WeightRepository;
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
        if (isValidReference(weight)) {
            return weightRepository.save(weight);
        }
        weight.setReference(-1L);
        return weightRepository.save(weight);
    }

    public Weight updateWeight(Long id, Weight weight) {
        Weight exsistingWeight = getWeightById(id);
        exsistingWeight.setMagWeight(weight.getMagWeight());
        exsistingWeight.setPhaseWeight(weight.getPhaseWeight());
        exsistingWeight.setPlane(weight.getPlane());
        exsistingWeight.setReference(weight.getReference());
        exsistingWeight.setMagTotalWeight(weight.getMagTotalWeight());
        exsistingWeight.setPhaseTotalWeight(weight.getPhaseTotalWeight());
        return weightRepository.save(exsistingWeight);
    }

    public void deleteWeight(Long id) {
        weightRepository.deleteById(id);
    }


    public List<Weight> getWeightsByUnit(Unit unit) {
        if (weightRepository.findByUnit(unit).isPresent()) {
            return weightRepository.findByUnit(unit).get();
        }
        return null;
    }

    public Weight getWeightByUnitAndInsideId(Unit unit, Long insideId) {
        if (weightRepository.findByUnitAndInsideId(unit, insideId).isPresent()) {
            return weightRepository.findByUnitAndInsideId(unit, insideId).get();
        }
        return null;
    }

    public Weight updateTotalValue(Long id, Weight weight) {
        List<Weight> weights = getWeightsByUnit(weight.getUnit());
        long reference = weight.getReference();
        Complex complexTotalWeight = weight.getComplexWeight();
        Optional<Weight> tempWeight;
        while (reference != -1) {
            long finalReference = reference;
            tempWeight = weights
                    .stream()
                    .filter(w -> w.getInsideId()
                            == finalReference).findFirst();
            if (tempWeight.isPresent()) {
                complexTotalWeight = complexTotalWeight
                        .plus(tempWeight.get().getComplexWeight());
                reference = tempWeight.get().getReference();
            } else {
                reference = -1;
                weight.setSystemInformation("Invalid reference in a chain");
            }
        }
        weight.setComplexTotalWeight(complexTotalWeight);
        weight.setMagTotalWeight(complexTotalWeight.abs());
        weight.setPhaseTotalWeight(complexTotalWeight.phase());
        updateWeight(id, weight);
        return weight;
    }


    public boolean fixCycles(Unit unit) {
        for (Weight weight : unit.getWeights()) {
            if (isValidReference(weight)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidReference(Weight weight) {
        Set<Long> visited = new HashSet<>();
        var weights = getWeightsByUnit(weight.getUnit());
        if (weights != null) {
            while (weight.getReference() != -1) {
                if (visited.contains(weight.getInsideId())) {
                    return false;
                }
                visited.add(weight.getInsideId());
                long finalReference = weight.getReference();
                Optional<Weight> weightNext = weights.stream()
                        .filter(w -> w.getInsideId() == finalReference).findFirst();
                if (weightNext.isEmpty()) {
                    return false;
                }
                weight = weightNext.get();
            }
            return true;
        }
        return true;
    }
}
