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
        List<Weight> weights = weightService.getWeightByUnit(weight.getUnit());
        long reference = weight.getReference();
        long tempId;
        Complex totalWeight = weight.getComplexWeight();
        Optional<Weight> tempWeight;
        while (reference != -1) {
            try {
                tempWeight = weights.stream()
                        .filter(w -> w.getInsideId() == reference)
                        .findFirst();
            totalWeight.plus(tempWeight.);
            }
        } while (reference != -1) ;
        Weight tempWeight = weight;
        Complex totalComplexWeight = weight.getComplexWeight();
        tempId = .getReference();
        try {
            totalComplexWeight = totalComplexWeight.
                    plus(weights.
                    . (tempId)
                    .getComplexWeight());
            tempRecord = recordService
                    .getRecordById(tempId);
        } catch (RuntimeException e) {
            tempRecord.setReference(-1L);
            recordService.updateRecord(tempRecord.getId(),
                    tempRecord);
            System.out.println(e.getMessage());
        }

        record.setComplexTotalWeight(totalComplexWeight);
    }
}

}
