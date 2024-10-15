package com.example.balancing.services.unit;

import com.example.balancing.exception.unit.UnitNotFoundException;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(UnitNotFoundException::new);
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setPlanes(unit.getPlanes());
        existingUnit.setModes(unit.getModes());
        existingUnit.setPoints(unit.getPoints());
        existingUnit.setDescription(unit.getDescription());
        existingUnit.setVibrationPrecision(unit.getVibrationPrecision());
        existingUnit.setVibrationUnitMeasure(unit.getVibrationUnitMeasure());
        existingUnit.setWeightPrecision(unit.getWeightPrecision());
        existingUnit.setWeightUnitMeasure(unit.getWeightUnitMeasure());
        return createUnit(existingUnit);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    public Unit getFilledUnitById(Long id) {
        return calculateSensitivities(getUnitById(id));
    }

    private Unit calculateTotalWeights(Unit unit) {

        List<Run> runs = unit.getRuns().stream()
                .sorted(Comparator.comparing(Run::getNumber)).toList();

        Complex totalWeight = new Complex(0d, 0d);

        for (Run run : runs) {
            Run refRun = run.getReferenceRun();
            if (refRun != null) {
                totalWeight.plus(refRun.getWeight().getComplexWeight());
            } else {
                Weight weight = run.getWeight();
                totalWeight.plus(run.getWeight().getComplexWeight());
                weight.setComplexTotalWeight(totalWeight);
                run.setWeight(weight);
            }
        }

        unit.setRuns(runs);
        return unit;
    }

    private Unit calculateSensitivities(Unit unit) {

        calculateTotalWeights(unit);
        var runs = unit.getRuns();
        for (Run run : runs) {

            var refRun = run.getReferenceRun();

            if (refRun != null) {
                var currentRecords = run.getWeight().getRecords();

                for (Record record : currentRecords) {
                    var matchedRefRecords = refRun.getWeight().getRecords().stream()
                            .filter(r -> r.getPoint().equals(record.getPoint()))
                            .filter(r -> r.getMode().equals(record.getMode()))
                            .toList();

                    matchedRefRecords.forEach(r ->
                            r.setComplexSensitivity(
                            record.calculateComplexSensitivity(r.getRecord())));
                }
            }
        }

        return unit;
    }

}