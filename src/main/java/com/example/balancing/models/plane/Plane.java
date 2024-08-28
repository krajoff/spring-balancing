package com.example.balancing.models.plane;

import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Сущность плоскости установки груза, как реального, так и вычисленного.
 * Содержит информацию о названии места (ротор, верхний вентилятор,
 * сторона возбудителя, просто цифра и т.д.) и список грузов в данной
 * плоскости агрегата
 */
@Entity
@Table(name = "planes")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max = 20)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            mappedBy = "plane", orphanRemoval = true)
    @ToString.Exclude
    private List<Weight> weights;

}
