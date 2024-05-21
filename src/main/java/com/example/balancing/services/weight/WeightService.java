package com.example.balancing.services.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;

import java.util.List;

public interface WeightService {

    Weight getWeightById(Long id);

    Weight createWeight(Weight weight);

    Weight updateWeight(Long id, Weight weight);

    void deleteWeight(Long id);

    Weight calculateTotalWeight(Long id);

    List<Weight> getWeightByUnit(Unit unit);
}
