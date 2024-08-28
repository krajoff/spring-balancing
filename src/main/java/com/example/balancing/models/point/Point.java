package com.example.balancing.models.point;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Сущность точки измерения вибрации. Содержит информацию
 * о названии места и списка записей вибрации в данной точке
 */
@Entity
@Table(name = "points")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max = 10)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "point", orphanRemoval = true)
    @ToString.Exclude
    private List<Record> records;

}

