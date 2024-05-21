package com.example.balancing.services.record;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import com.example.balancing.repository.RecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CycleCheckerService {

    @Autowired
    private RecordRepository recordRepository;

    @Transactional
    public boolean hasCycles(Unit unit) {
        for (Record record : unit.getRecords()) {
            if (isCyclic(record, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCyclic(Record record, Set<Long> visited) {
        if (record.getReference() == -1) {
            return false;
        }
        if (visited.contains(record.getId())) {
            return true;
        }
        visited.add(record.getId());
        Optional<Record> nextRecord = recordRepository.findById(record.getReference());
        return nextRecord.isPresent() && isCyclic(nextRecord.get(), visited);
    }
}
