package com.example.balancing.repository;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findByUnit(Unit unit);

    @Query(value = "select * from weights w where w.unit = ?1 and w.inside = ?2",
    nativeQuery = true)
    Weight findByUnitAndInsideId(Unit unit, Long insideId);
}


