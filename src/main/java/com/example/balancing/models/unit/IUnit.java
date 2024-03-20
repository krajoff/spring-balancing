package com.example.balancing.models.unit;

import com.example.balancing.models.record.Record;

import java.util.List;

public interface IUnit {
    public List<Record> addRecord(Record record);
}
