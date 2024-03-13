package com.example.balancing.repository;

import com.example.balancing.models.target.Target;
import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findByUnit(Unit unit);

}
