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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TargetWeightService {
    @Autowired
    private MappingUtils mappingUtils;

    /**
     *
     * @param firstRecord is reference record
     * @param secondRecord is trial record and used to set fields for target weight
     * @return Weight is target weight
     */
    public Weight calculateTargetWeight(Record firstRecord, Record secondRecord) {
        Complex zVib1, zVib2, zWgt1, zWgt2, dVib, dWgt, foo1, foo2;
        zVib1 = firstRecord.getComplexVibration();
        zVib2 = secondRecord.getComplexVibration();
        zWgt1 = firstRecord.getWeight().getComplexWeight();
        zWgt2 = secondRecord.getWeight().getComplexWeight();
        dVib = zVib2.minus(zVib1);
        dWgt = zWgt2.minus(zWgt1);
        Double magSensitivity = dVib.divides(dWgt).abs();
        Double phaseSensitivity = Math.toDegrees(dVib.divides(dWgt).phase());
        foo1 = dWgt.times(zVib1);
        foo2 = foo1.divides(dVib);
        Weight targetWeight = new Weight();
        targetWeight.setComplexWeight(zWgt1.minus(foo2));
        targetWeight.setTarget(true);
        targetWeight.setNumberRun(secondRecord.getWeight().getNumberRun());
        targetWeight.setPlane(secondRecord.getWeight().getPlane());
        targetWeight.setUnit(secondRecord.getWeight().getUnit());
        return targetWeight;
    }

}
