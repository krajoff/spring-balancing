package com.example.balancing.models.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Entity
@Table(name = "weights")
@Data
@NoArgsConstructor
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plane", columnDefinition = "integer default 1")
    private Integer plane;

    @Column(name = "number_run")
    private Integer numberRun;

    @Column(name = "reference")
    private Integer reference;

    @Column(name = "mag_ref_weight")
    private Double magRefWeight;

    @Column(name = "phase_ref_weight")
    private Double phaseRefWeight;

    @Transient
    private Complex complexRefWeight;

    @Column(name = "mag_weight")
    private Double magWeight;

    @Column(name = "phase_weight")
    private Double phaseWeight;

    @Transient
    private Complex complexWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "weight",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;

    @Column(name = "system_information")
    private String systemInformation;

    @Column(name = "is_target", columnDefinition = "boolean default false")
    private boolean isTarget;

    public Complex getComplexRefWeight() {
        return new Complex(this.magRefWeight *
                cos(Math.toRadians(this.phaseRefWeight)),
                this.magRefWeight * sin(Math.toRadians(this.phaseRefWeight)));
    }

    public Double getMagRefWeight() {
        return roundAvoid(getComplexRefWeight().abs(), 1);
    }

    public Double getPhaseRefWeight() {
        return roundAvoid(Math
                .toDegrees(getComplexRefWeight()
                        .phase()), 0);
    }

    public void setPhaseRefWeight(Double phaseRefWeight) {
        this.phaseRefWeight = Math.toDegrees(phaseRefWeight);
    }

    public Complex getComplexWeight() {
        return new Complex(this.magWeight *
                cos(Math.toRadians(this.phaseWeight)),
                this.magWeight * sin(Math.toRadians(this.phaseWeight)));
    }

    public Double getMagWeight() {
        return roundAvoid(getComplexWeight().abs(), 1);
    }

    public Double getPhaseWeight() {
        return roundAvoid(Math
                .toDegrees(getComplexWeight()
                        .phase()), 0);
    }

    public void setPhaseWeight(Double phaseWeight) {
        this.phaseWeight = Math.toDegrees(phaseWeight);
    }

    private Double roundAvoid(Double value, Integer places) {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public Weight getWeight() {
        return this;
    }

}




