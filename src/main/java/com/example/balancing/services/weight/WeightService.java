package com.example.balancing.services.weight;

import com.example.balancing.models.weight.Weight;

public interface WeightService {

    Weight getWeightById(Long id);

    Weight createWeight(Weight weight);

    Weight updateWeight(Long id, Weight weight);

    void deleteWeight(Long id);


}
