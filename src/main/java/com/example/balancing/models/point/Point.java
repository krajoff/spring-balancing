package com.example.balancing.models.point;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "points")
@Data
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "point_id", nullable = false)
    private Integer pointId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "record")
    private List<Record> records;

    @Column(name = "name")
    @Size(max = 50)
    private String name;
}
