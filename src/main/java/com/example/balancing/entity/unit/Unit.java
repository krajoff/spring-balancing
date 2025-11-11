package com.example.balancing.entity.unit;

import com.example.balancing.entity.mode.Mode;
import com.example.balancing.entity.plane.Plane;
import com.example.balancing.entity.point.Point;
import com.example.balancing.entity.run.Run;
import com.example.balancing.entity.station.Station;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Unit")
@Table(name = "units")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plane> planes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mode> modes = new ArrayList<>();

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points;

    @Column(name = "weight_precision")
    @Size(max = 2)
    private Integer weightPrecision;

    @Column(name = "weight_unit_measure")
    @Size(max = 5)
    private String weightUnitMeasure;

    @Column(name = "vibration_precision")
    @Size(max = 2)
    private Integer vibrationPrecision;

    @Column(name = "vibration_unit_measure")
    @Size(max = 5)
    private String vibrationUnitMeasure;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station station;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Version
    @Column(name = "version")
    private Long version = 1L;

}
