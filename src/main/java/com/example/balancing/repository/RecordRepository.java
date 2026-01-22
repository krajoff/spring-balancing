package com.example.balancing.repository;

import com.example.balancing.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    @Query("""
                select r from Record r
                join r.run run
                join run.unit u
                join u.station s
                where r.id = :recordId
                and s.user.id = :userId
            """)
    Optional<Record> findByIdAndUserId(UUID recordId, UUID userId);

    @Query("""
                select r from Record r
                join r.run run
                join run.unit u
                join u.station s
                where run.id = :runId
                and s.user.id = :userId
            """)
    List<Record> findByRunIdAndUserId(UUID runId, UUID userId);

    @Query("""
                select r from Record r
                join r.run run
                join run.unit u
                join u.station s
                where u.id = :unitId
                and s.user.id = :userId
            """)
    List<Record> findByUnitIdAndUserId(UUID unitId, UUID userId);

    @Modifying
    @Query("""
                delete from Record r
                where r.id = :recordId
                and r.run.id in (
                    select run.id from Run run
                    join run.unit u
                    join u.station s
                    where s.user.id = :userId
                )
            """)
    void deleteByIdAndUserId(UUID recordId, UUID userId);
}
