package com.example.balancing.services.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.weight.Weight;
import org.springframework.stereotype.Service;

@Service
public interface TargetWeightService {

    /**
     *
     * @param beforeRecord запись до изменения веса
     * @param afterRecord запись после изменения веса
     * @return Weight балансировочный груз
     */
    public Weight calculateTargetWeight(Record beforeRecord, Record afterRecord) {
        Complex zVib1, zVib2, zWgt1, zWgt2, dVib, dWgt, foo1, foo2;
        zVib1 = beforeRecord.getComplexVibration();
        zVib2 = afterRecord.getComplexVibration();
        zWgt1 = beforeRecord.getWeight().getComplexWeight();
        zWgt2 = afterRecord.getWeight().getComplexWeight();
        dVib = zVib2.minus(zVib1);
        dWgt = zWgt2.minus(zWgt1);
        Double magSensitivity = dVib.divides(dWgt).abs();
        Double phaseSensitivity = Math.toDegrees(dVib.divides(dWgt).phase());
        foo1 = dWgt.times(zVib1);
        foo2 = foo1.divides(dVib);
        Weight targetWeight = new Weight();
        targetWeight.setComplexWeight(zWgt1.minus(foo2));
        targetWeight.setTarget(true);
        targetWeight.setNumberRun(afterRecord.getWeight().getNumberRun());
        targetWeight.setPlane(afterRecord.getWeight().getPlane());
        targetWeight.setUnit(afterRecord.getWeight().getUnit());
        return targetWeight;
    }

}
