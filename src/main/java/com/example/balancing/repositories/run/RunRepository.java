package com.example.balancing.repositories.run;

import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {
    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = ?", nativeQuery = true)
    Optional<List<Run>> findByUnitId(Long id);

    @Query(value = "SELECT * FROM runs r WHERE r.weight_id = ?", nativeQuery = true)
    Optional<Run> findByWeightId(Long id);

    @Modifying
    @Query(value = "UPDATE Run r SET r.reference_run_id = NULL " +
            "WHERE r.reference_run_id = ?", nativeQuery = true)
    void setNullReference(Long id);

    @Modifying
    @Query(value = "UPDATE Run r SET r.number = r.number - 1 WHERE r.number > ?",
            nativeQuery = true)
    void alterNumberRun(Integer threshold);

}
