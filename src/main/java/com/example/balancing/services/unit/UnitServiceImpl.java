package com.example.balancing.services.unit;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.dto.WeightDto;
import com.example.balancing.models.place.Place;
import com.example.balancing.models.unit.Unit;
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

    public Unit getFullUnitById(Long id) {
        Unit unit = getUnitById(id);
        List<WeightDto> weights = unit.getWeights()
                .stream()
                .map(mappingUtils::mapToWeightDto).toList();

        if (weights.size() > 1) {
            for (WeightDto weight : weights) {
                Integer reference = weight.getReference();
                if (reference != -1) {
                    Map<Place, RecordDto> initialRecords = weight.getRecords()
                            .stream()
                            .collect(Collectors
                                    .toMap(RecordDto::getPlace, RecordDto::getRecord));

                    Optional<WeightDto> refWeight = unit.getWeights().stream()
                            .filter(w -> w.getNumberRun().equals(reference))
                            .map(mappingUtils::mapToWeightDto)
                            .findFirst();

                    Map<Place, RecordDto> refRecords = null;

                    if (refWeight.isPresent()) {
                        refRecords = refWeight.get()
                                .getRecords().stream()
                                .collect(Collectors.toMap(RecordDto::getPlace, RecordDto::getRecord));
                    }

                    if (refRecords != null) {
                        for (Place place : initialRecords.keySet()) {
                            try {
                                Weight targetWeight = targetWeightService
                                        .calculateTargetWeight(initialRecords.get(place), refRecords.get(point));
                                initialRecords.get(place).setWeight(targetWeight);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                    weight.setRecords(initialRecords);
                }
            }
            unit.setWeights();

        }
        return unit;