package com.example.balancing.services.weight;

import com.example.balancing.exception.weight.WeightNotFoundException;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.weight.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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
        existingWeight.setMagWeight(weight.getMagWeight());
        existingWeight.setPhaseWeight(weight.getPhaseWeight());
        existingWeight.setRecords(weight.getRecords());
        existingWeight.setSystemInformation(weight.getSystemInformation());
        return weightRepository.save(existingWeight);
    }

    public void deleteWeight(Long id) {
        weightRepository.deleteById(id);
    }

}
