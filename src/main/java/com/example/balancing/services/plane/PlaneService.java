package com.example.balancing.services.plane;

import com.example.balancing.models.plane.Plane;

public interface PlaneService {
    Plane getPlaneById(Long id);

    Plane createPlane(Plane plane);

    Plane updatePlane(Long id, Plane plane);

    void deletePlane(Long id);
}
