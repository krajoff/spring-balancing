package com.example.balancing.models.unit;

import com.example.balancing.models.user.User;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit implements IUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "inside_id")
    private Long insideId = 0L;

    @Column(name = "record_count")
    private Long recordCount;

    @Column(name = "station")
    @Size(max = 50)
    private String station;

    @Column(name = "unit_number")
    @Size(max = 50)
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
