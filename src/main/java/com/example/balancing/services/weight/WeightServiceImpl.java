package com.example.balancing.services.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightServiceImpl implements WeightService{

    @Autowired
    private WeightRepository weightRepository;

    public Weight getWeightById(Long id) {
        return weightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weight not found"));
    }

    public Weight createWeight(Weight weight) {
        return weightRepository.save(weight);
    }

    public Weight updateWeight(Long id, Weight weight) {
        Weight exsistingWeight = getWeightById(id);
        exsistingWeight.setMagWeight(weight.getMagWeight());
        exsistingWeight.setPhaseWeight(weight.getPhaseWeight());
        exsistingWeight.setPlane(weight.getPlane());
        exsistingWeight.setReference(weight.getReference());
        return weightRepository.save(exsistingWeight);
    }

    public void deleteWeight(Long id) {
        weightRepository.deleteById(id);
    }

    public Weight calculateTotalWeight(Long id) {
        return null;
    }

    public List<Weight> getWeightByUnit(Unit unit) {
        return weightRepository.findByUnit(unit);
    }

    public Weight getWeightByUnitAndInsideId(Unit unit, Long insideId){
        return weightRepository.findByUnitAndInsideId(unit, insideId);
    }
}
