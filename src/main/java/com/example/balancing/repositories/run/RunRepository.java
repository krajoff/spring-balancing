package com.example.balancing.repositories.run;

import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {
    @Query(value = "SELECT * FROM runs r WHERE r.unit_id = ?", nativeQuery = true)
    List<Run> findByUnitId(Long id);
}
