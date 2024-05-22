package com.example.balancing.services.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.weight.Weight;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TotalWeightService {

    @Autowired
    private WeightService weightService;

    public Weight getTotalWeight(Weight weight) {
        List<Weight> weights = weightService
                .getWeightByUnit(weight.getUnit());
        long reference = weight.getReference();
        Complex complexTotalWeight = weight
                .getComplexWeight();
        Optional<Weight> tempWeight;
        while (reference != -1) {
            long finalReference = reference;
            tempWeight = weights
                    .stream()
                    .filter(w -> w.getInsideId()
                            == finalReference).findFirst();
            if (tempWeight.isPresent()) {
                complexTotalWeight = complexTotalWeight
                        .plus(tempWeight.get()
                                .getComplexWeight());
                reference = tempWeight.get().getReference();
            } else {
                reference = -1;
                weight.setSystemInformation("Invalid reference in a chain");
            }
        }
        weight.setComplexTotalWeight(complexTotalWeight);
        weight.setMagTotalWeight(complexTotalWeight.abs());
        weight.setPhaseTotalWeight(complexTotalWeight.phase());
        weightService.createWeight(weight);
        return weight;
    }

}
