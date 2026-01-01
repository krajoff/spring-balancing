package com.example.balancing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.balancing.entity.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    List<Record> findByRunId(UUID runId);

}
