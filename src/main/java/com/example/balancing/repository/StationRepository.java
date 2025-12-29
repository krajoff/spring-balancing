package com.example.balancing.repository;

import com.example.balancing.entity.Station;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @NonNull
    Optional<Station> findByIdAndUserId(@NonNull Long id, @NonNull Long userId);

    List<Station> findAllByUserId(Long userId);

}


