package com.example.balancing.repositories.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    Optional<List<Weight>> findByUnit(Unit unit);

    @Query(value = "SELECT * FROM weights w WHERE w.unit = ?1 AND w.number_run = ?2",
            nativeQuery = true)
    Optional<Weight> findByUnitAndNumberRun(Unit unit, Integer numberRun);

}


