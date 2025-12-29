package com.example.balancing.repository;

import com.example.balancing.entity.run.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RunRepository extends JpaRepository<Run, UUID> {

    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = ?", nativeQuery = true)
    Optional<List<Run>> findByUnitId(UUID id);

}
