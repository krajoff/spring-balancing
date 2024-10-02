package com.example.balancing.services.unit;

import com.example.balancing.exception.UnitNotFoundException;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Unit getWholeUnitById(Long id) {
        Unit unit = getUnitById(id);
        List<Plane> planes = unit.getPlanes();


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