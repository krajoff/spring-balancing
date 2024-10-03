package com.example.balancing.services.plane;

import com.example.balancing.exception.PlaneNotFoundException;
import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.weight.Weight;
import com.example.balancing.repositories.plane.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PlaneServiceImpl implements PlaneService {

    @Autowired
    PlaneRepository planeRepository;

    @Override
    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id).orElseThrow(PlaneNotFoundException::new);
    }

    @Override
    public Plane createPlane(Plane plane) {
        return planeRepository.save(plane);
    }

    @Override
    public Plane updatePlane(Long id, Plane plane) {
        Plane existingPlane = getPlaneById(id);
        existingPlane.setNumber(plane.getNumber());
        return createPlane(existingPlane);
    }

    @Override
    public void deletePlane(Long id) {
        planeRepository.deleteById(id);
    }

    public Weight calculateTotalWeight() {
        List<Weight> weights = getWeightsByUnit(weight.getUnit());
        long reference = weight.getReference();
        Complex complexWeight = weight.getComplexRefWeight();
        Optional<Weight> tempWeight;
        while (reference != -1) {
            long finalReference = reference;
            tempWeight = weights
                    .stream()
                    .filter(w -> w.getNumberRun()
                            == finalReference).findFirst();
            if (tempWeight.isPresent()) {
                complexWeight = complexWeight
                        .plus(tempWeight.get().getComplexRefWeight());
                reference = tempWeight.get().getReference();
            } else {
                reference = -1;
                weight.setSystemInformation("Invalid reference in a chain");
            }
        }
        weight.setComplexWeight(complexWeight);
        weight.setMagWeight(complexWeight.abs());
        weight.setPhaseWeight(complexWeight.phase());
        return weight;
    }

}
