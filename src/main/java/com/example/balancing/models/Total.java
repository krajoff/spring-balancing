package com.example.balancing.models;

import com.example.balancing.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Component
public class Total {

    @Autowired
    private RecordService recordService;

    public Complex getTotalWeight(Long id) {
        Record record = recordService.getRecordById(id);
        return record.getComplexWeight();
//        while (record.getReference() != -1) {
//            record = recordService.getRecordById(record.getReference());
//            result = result.plus(record.getComplexWeight());
//        }
//        return result;
    }
}