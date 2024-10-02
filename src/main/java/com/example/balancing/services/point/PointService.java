package com.example.balancing.services.point;

import com.example.balancing.models.point.Point;

public interface PointService {
    Point getPointById(Long id);

    Point createPoint(Point point);

    Point updatePoint(Long id, Point point);

    void deletePoint(Long id);
}
