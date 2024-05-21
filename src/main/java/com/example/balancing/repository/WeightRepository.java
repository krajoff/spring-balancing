package com.example.balancing.repository;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.target.Target;
import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightRepository extends JpaRepository<Record, Long> {
    List<Target> findByUnit(Unit unit);
}


