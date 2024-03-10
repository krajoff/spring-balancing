package com.example.balancing.repository;

import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
