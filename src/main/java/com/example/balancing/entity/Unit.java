package com.example.balancing.entity;

import com.example.balancing.entity.run.Run;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Unit")
@Table(name = "units")
public class Unit {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    private UUID id;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @Column(name = "unit_type")
    private String unitType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs;

    @Column(name = "weight_precision")
    private Integer weightPrecision;

    @Column(name = "weight_unit_measure")
    private String weightUnitMeasure;

    @Column(name = "vibration_precision")
    private Integer vibrationPrecision;

    @Column(name = "vibration_unit_measure")
    private String vibrationUnitMeasure;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Version
    @Column(name = "version")
    private Long version = 1L;

}
