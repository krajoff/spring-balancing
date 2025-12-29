package com.example.balancing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.balancing.entity.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    @Query(value = "select * from records r where r.mode = ?1",  nativeQuery = true)
    List<Record> findByMode(String mode);

    @Query(value = "select * from records r where r.weight_id = ?1",  nativeQuery = true)
    List<Record> findByWeight(UUID id);

    @Query(value = "select * from records r join weights w on r.weight_id = w.id " +
            "join unit u on w.unit_id = u.id where r.weight_id = ?1",  nativeQuery = true)
    List<Record> findByWeightAndUnit(UUID id);

}
