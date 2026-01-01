package com.example.balancing.repository;

import com.example.balancing.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {

    @Query("""
                select u from Unit u
                join u.station s
                where u.id = :id
                and s.user.id = :userId
            """)
    Optional<Unit> findByIdAndUserId(UUID id, UUID userId);

    @Query("""
                select u from Unit u
                join u.station s
                where s.id = :stationId
                and s.user.id = :userId
            """)
    List<Unit> findByStationIdAndUserId(UUID stationId, UUID userId);

    @Modifying
    @Query("""
                delete from Unit u
                where u.id = :id
                and u.station.id in (
                    select s.id from Station s
                    where s.user.id = :userId
                )
            """)
    void deleteByIdAndUserId(UUID id, UUID userId);

}
