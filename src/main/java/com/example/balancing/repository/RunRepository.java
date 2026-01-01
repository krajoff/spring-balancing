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

    @Query("""
                select r from Run r
                join r.unit u
                join u.station s
                where r.id = :runId
                and s.user.id = :userId
            """)
    Optional<Run> findByIdAndUserId(@Param("runId") UUID runId, @Param("userId") UUID userId);

    @Query("""
                select r from Run r
                join r.unit u
                join u.station s
                where u.id = :unitId
                and s.user.id = :userId
            """)
    Optional<List<Run>> findByUnitIdAndUserId(@Param("unitId") UUID unitId, @Param("userId") UUID userId);

    @Query(value = """
            select r from Run r
            where r.unit.id = :unitId
            """)
    Optional<List<Run>> findByUnitId(@Param("unitId") UUID unitId);

    @Query(value = """
            select r from Run r
            where r.unit.id = :unitId
            and r.referenceRunId = :referenceRunId
            """)
    Optional<List<Run>> findByUnitIdAndReferenceRunId(@Param("unitId") UUID unitId,
                                                      @Param("referenceRunId") UUID referenceRunId);

    @Query(value = """
            select r from Run r
            where r.unit.id = :unitId
            and r.runNumber = :runNumber
            """)
    Optional<Run> findByUnitIdAndRunNumber(@Param("unitId") UUID unitId,
                                           @Param("runNumber") Integer runNumber);

}
