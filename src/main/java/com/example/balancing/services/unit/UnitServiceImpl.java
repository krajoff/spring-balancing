package com.example.balancing.services.unit;

import com.example.balancing.models.plane.Place;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.weight.TargetWeightService;
import com.example.balancing.services.weight.WeightService;
import com.example.balancing.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private RecordService recordService;
    @Autowired
    private WeightService weightService;
    @Autowired
    private TargetWeightService targetWeightService;
    @Autowired
    private MappingUtils mappingUtils;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    public List<Unit> getUnitsByUserId(Long id) {
        return unitRepository.findByUserId(id);
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit existingUnit = getUnitById(id);
        existingUnit.setType(unit.getType());
        existingUnit.setStation(unit.getStation());
        existingUnit.setUnitNumber(unit.getUnitNumber());
        existingUnit.setDescription(unit.getDescription());
        return unitRepository.save(existingUnit);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }

    public Unit getWholeUnitById(Long id) {
        Unit unit = getUnitById(id);
        List<Weight> weights = unit.getWeights();

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