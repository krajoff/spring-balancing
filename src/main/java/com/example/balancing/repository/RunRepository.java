package com.example.balancing.repository;

import com.example.balancing.entity.run.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RunRepository extends JpaRepository<Run, UUID> {

    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = :unitId", nativeQuery = true)
    Optional<List<Run>> findByUnitId(@Param("unitId") UUID unitId);

    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = :unitId AND r.reference_run_id = :referenceRunId", nativeQuery = true)
    Optional<List<Run>> findByUnitIdAndReferenceRunId(@Param("unitId") UUID unitId,
                                                      @Param("referenceRunId") UUID referenceRunId);

    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = :unitId AND r.run_number = :runNumber", nativeQuery = true)
    Optional<Run> findByUnitIdAndRunNumber(@Param("unitId") UUID unitId,
                                           @Param("runNumber") Integer runNumber);

}
