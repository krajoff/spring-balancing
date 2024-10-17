package com.example.balancing.services.plane;

import com.example.balancing.exception.plane.PlaneNotFoundException;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.repositories.plane.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

}
