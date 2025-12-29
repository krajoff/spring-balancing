package com.example.balancing.repository;

import com.example.balancing.entity.Station;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StationRepository extends JpaRepository<Station, UUID> {

    @NonNull
    Optional<Station> findByIdAndUserId(@NonNull UUID id, @NonNull UUID userId);

    List<Station> findAllByUserId(UUID userId);

}


