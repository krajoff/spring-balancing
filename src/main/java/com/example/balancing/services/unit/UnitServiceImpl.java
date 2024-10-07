package com.example.balancing.services.unit;

import com.example.balancing.exception.UnitNotFoundException;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Unit calculateUnitById(Long id) {
        Unit unit = getUnitById(id);
        List<Plane> planes = unit.getPlanes();
        calculateTotalWeights(unit);
;
        if (weights.size() > 1) {
            for (Weight weight : weights) {
                Integer reference = weight.getReference();
                if (reference != -1) {
                    Map<Place, Record> initialRecords = weight.getRecords()
                            .stream()
                            .collect(Collectors
                                    .toMap(Record::getPlace, Record::getRecord));

                    Optional<Weight> refWeight = unit.getWeights().stream()
                            .filter(w -> w.getNumberRun().equals(reference))
                            .findFirst();

                    Map<Place, Record> refRecords = null;

                    if (refWeight.isPresent()) {
                        refRecords = refWeight.get()
                                .getRecords().stream()
                                .collect(Collectors
                                        .toMap(Record::getPlace, Record::getRecord));
                    }

                    if (refRecords != null) {
                        for (Place place : initialRecords.keySet()) {
                            try {
                                Weight targetWeight = targetWeightService
                                        .calculateTargetWeight(initialRecords.get(place),
                                                refRecords.get(place));
                                initialRecords.get(place).setWeight(targetWeight);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                    weight.setRecords(new ArrayList<>(initialRecords.values()));
                }
            }
            unit.setWeights(weights);

        }
        return unit;
    }
}