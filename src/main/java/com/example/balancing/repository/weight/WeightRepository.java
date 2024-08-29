package com.example.balancing.repository.weight;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    Optional<List<Weight>> findByUnit(Unit unit);

    @Query(value = "select * from weights w where w.unit = ?1 and w.number_run = ?2",
            nativeQuery = true)
    Optional<Weight> findByUnitAndNumberRun(Unit unit, Integer numberRun);
}


