package com.example.balancing.models.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Entity
@Table(name = "weights")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Weight implements IWeight{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Long id;

    @Column(name = "inside_id")
    @NonNull
    private Long insideId;

    @Column(name = "plane", columnDefinition = "integer default 1")
    @NonNull
    private Integer plane;

    @Column(name = "mag_weight", nullable = false)
    @NonNull
    private Double magWeight;

    @Column(name = "phase_weight", nullable = false)
    @NonNull
    private Double phaseWeight;

    @Column(name = "reference", columnDefinition = "bigint default -1")
    @NonNull
    private Long reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Transient
    private Double magTotalWeight;
    @Transient
    private Double phaseTotalWeight;

    public Complex getComplexWeight() {
        return new Complex(this.magWeight *
                cos(Math.toRadians(this.phaseWeight)),
                this.magWeight * sin(Math.toRadians(this.phaseWeight)));
    }

    private Double roundAvoid(Double value, Integer places) {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}




