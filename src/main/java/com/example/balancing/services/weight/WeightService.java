package com.example.balancing.services.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;

import java.util.List;
import java.util.Optional;

public interface WeightService {

    Weight getWeightById(Long id);

    List<Weight> getWeightsByUnit(Unit unit);

    Weight getWeightByUnitAndInsideId(Unit unit, Long id);

    Weight createWeight(Weight weight);

    Weight updateWeight(Long id, Weight weight);

    void deleteWeight(Long id);

    Weight updateTotalValue(Weight weight);

}
