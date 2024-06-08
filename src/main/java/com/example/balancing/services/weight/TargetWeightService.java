package com.example.balancing.services.weight;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import com.example.balancing.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TargetWeightService {

    @Autowired
    private WeightService weightService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UnitService unitService;
    @Autowired
    private MappingUtils mappingUtils;


    public Unit calculateTargetWeight(Long id) {
        Unit unit = unitService.getUnitById(id);
        Map<Weight, List<Record>> records = unit.getWeights()
                .stream()
                .collect(Collectors.toMap(Weight::getWeight, Weight::getRecords));
        Record secondRecord;
        long secondId;
        double magSensitivity, phaseSensitivity;
        Complex zVib1, zVib2, zWgt1, zWgt2, dVib, dWgt, sen, foo1, foo2;
        Complex targetWeight;
        for(Weight weight: records.keySet())
        for (Record firstRecord : records.get(weight)) {
            secondId = firstRecord.getReference();
            if (secondId != -1) {
                try {
                    secondRecord = recordService.getRecordById(secondId);
                    zVib1 = firstRecord.getComplexVibration();
                    zVib2 = secondRecord.getComplexVibration();
                    zWgt1 = firstRecord.getComplexTotalWeight();
                    zWgt2 = secondRecord.getComplexTotalWeight();
                    dVib = zVib2.minus(zVib1);
                    dWgt = zWgt2.minus(zWgt1);
                    sen = dVib.divides(dWgt);
                    mSensitivity = sen.abs();
                    pSensitivity = Math.toDegrees(sen.phase());
                    foo1 = dWgt.times(zVib1);
                    foo2 = foo1.divides(dVib);
                    targetWeight = zWgt1.minus(foo2);
                    firstRecord.setComplexTargetWeight(targetWeight);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    firstRecord
                            .setComplexTargetWeight(
                                    new Complex(0d, 0d));
                }
            } else {
                firstRecord.
                        setComplexTargetWeight(new Complex(0d, 0d));
            }
        }
        unit.setRecords(records);
        return unit;
    }

}
