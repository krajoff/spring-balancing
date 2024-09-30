package com.example.balancing.repositories.mode;

import com.example.balancing.models.mode.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepository extends JpaRepository<Mode, Long> {
}
