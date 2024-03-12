package com.example.balancing.models.unit;

import com.example.balancing.models.record.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "station")
    @Size(max = 50)
    private String station;

    @Column(name = "unitnumber")
    @Size(max = 50)
    private Integer unitnumber;

    @Column(name = "type")
    @Size(max = 50)
    private String type;

    @Column(name = "plate")
    @Size(max = 20)
    private Integer plate;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit")
    private List<Record> records;

    public List<Record> addRecord(Record record) {
        records.add(record);
        return records;
    }
}
