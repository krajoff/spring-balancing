package com.example.balancing.services.point;

import com.example.balancing.exception.PointNotFoundException;
import com.example.balancing.models.point.Point;
import com.example.balancing.repositories.point.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PointServiceImpl implements PointService {

    @Autowired
    PointRepository pointRepository;

    @Override
    public Point getPointById(Long id) {
        return pointRepository.findById(id).orElseThrow(PointNotFoundException::new);
    }

    @Override
    public Point createPoint(Point point) {
        return pointRepository.save(point);
    }

    @Override
    public Point updatePoint(Long id, Point point) {
        Point existingPoint = getPointById(id);
        existingPoint.setName(point.getName());
        return createPoint(existingPoint);
    }

    @Override
    public void deletePoint(Long id) {

    }
}
