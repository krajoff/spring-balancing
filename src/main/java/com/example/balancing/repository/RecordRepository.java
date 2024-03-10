package com.example.balancing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.balancing.models.record.Record;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select * from records r where r.mode = ?1",  nativeQuery = true)
    List<Record> findByMode(String mode);

}
