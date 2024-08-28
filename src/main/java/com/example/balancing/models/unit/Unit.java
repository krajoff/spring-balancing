package com.example.balancing.models.unit;

import com.example.balancing.models.user.User;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность агрегата. Содержит название станции, номер агрега, тип
 */
@Entity
@Table(name = "units")
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit implements IUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "station",
            columnDefinition = "varchar(50) default 'Station #1'")
    @Size(max = 50)
    private String station;

    @Column(name = "unit_number", columnDefinition = "integer default 1")
    @Size(max = 3)
    private Integer unitNumber;

    @Column(name = "type")
    @Size(max = 50)
    private String type;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @Column(name = "units_weight")
    @Size(max = 10)
    private String unitsWeight;

    @Column(name = "units_vibration")
    @Size(max = 10)
    private String unitsVibration;

    @Column(name = "weight_precision")
    private Integer weightPrecision;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Weight> weights;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void prePersist() {
        if (this.recordCount == null)
            this.recordCount = 0L;
    }

}
