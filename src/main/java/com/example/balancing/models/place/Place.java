package com.example.balancing.models.place;

import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Сущность места установки груза, как реального, так и вычисленного.
 * Содержит информацию о названии места (спицы ротора генератора,
 * верхний вентилятор, сторона возбудителя и т.д.) и список грузов
 * данного агрегата
 */
@Entity
@Table(name = "place")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "weight_id")
    private List<Weight> weights;

}
