package com.example.balancing.repository.station;

import com.example.balancing.models.station.Station;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
   @Query(value = "select * from weights w where w.unit = ?1 and w.number_run = ?2",
            nativeQuery = true)
    Optional<Weight> findByUnitAndNumberRun(Unit unit, Integer numberRun);
}


