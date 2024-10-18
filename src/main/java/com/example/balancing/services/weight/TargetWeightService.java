package com.example.balancing.services.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.weight.Weight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface TargetWeightService {

    /**
     * Вычисляет целевой вес на основе изменений вибрации и веса между двумя записями
     * {@link Record}: {@code beforeRecord} и {@code afterRecord}.
     * Метод определяет разницу в вибрации и весе между записями, затем рассчитывает
     * чувствительность (величину и фазу) этого изменения. В итоге вычисляется
     * целевой вес, который должен сбалансировать изменение вибрации.
     * <p>
     * Метод создает новый объект {@link Weight} с рассчитанным целевым
     * весом и связывает его с переданными записями и запуском.
     *
     * @param beforeRecord Запись {@link Record}, представляющая состояние до изменения.
     * @param afterRecord  Запись {@link Record}, представляющая состояние после изменения.
     * @return Рассчитанный объект {@link Weight}, содержащий целевой вес.
     */
    default Weight calculateTargetWeight(Record beforeRecord, Record afterRecord) {
        Complex zVib1, zVib2, zWgt1, zWgt2, dVib, dWgt, foo1, foo2;
        zVib1 = beforeRecord.getComplexVibration();
        zVib2 = afterRecord.getComplexVibration();
        zWgt1 = beforeRecord.getWeight().getComplexWeight();
        zWgt2 = afterRecord.getWeight().getComplexWeight();
        dVib = zVib2.minus(zVib1);
        dWgt = zWgt2.minus(zWgt1);

        Double magSensitivity = dVib.divides(dWgt).abs();
        Double phaseSensitivity = Math.toDegrees(dVib.divides(dWgt).phase());
        afterRecord.setMagSensitivity(magSensitivity);
        afterRecord.setPhaseSensitivity(phaseSensitivity);
        foo1 = dWgt.times(zVib1);
        foo2 = foo1.divides(dVib);

        Weight targetWeight = new Weight();
        targetWeight.setComplexWeight(zWgt1.minus(foo2));
        targetWeight.setIsTarget(true);
        targetWeight.setRun(afterRecord.getWeight().getRun());
        targetWeight.setPlane(afterRecord.getWeight().getPlane());

        List<Record> records = new ArrayList<>();
        records.add(beforeRecord);
        records.add(afterRecord);
        targetWeight.setRecords(records);
        return targetWeight;
    }

}
