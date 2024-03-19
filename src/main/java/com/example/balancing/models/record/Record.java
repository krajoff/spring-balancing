package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import lombok.*;

import static java.lang.Math.*;

@Entity
@Table(name = "records")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Record implements IRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Long id;
    @Column(name = "mode", nullable = false)
    @NonNull
    private String mode;
    @Column(name = "magvibration", nullable = false)
    @NonNull
    private Double magvibration;
    @Column(name = "phasevibration", nullable = false)
    @NonNull
    private Double phasevibration;
    @Column(name = "magweight", nullable = false)
    @NonNull
    private Double magweight;
    @Column(name = "phaseweight", nullable = false)
    @NonNull
    private Double phaseweight;
    @Column(name = "reference", columnDefinition = "bigint default -1")
    @NonNull
    private Long reference;
    @Column(name = "stage", columnDefinition = "boolean default true")
    private Boolean stage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;
    @Transient
    private Complex complexVibration;
    @Transient
    private Complex complexWeight;
    @Transient
    private Complex complexTotalWeight;
    @Transient
    private Double magTotalWeight;
    @Transient
    private Double phaseTotalWeight;
    @Transient
    private Complex complexTotalVibration;
    @Transient
    private Double magTotalVibration;
    @Transient
    private Double phaseTotalVibration;

    public Complex getComplexVibration() {
        return new Complex(this.magvibration * cos(Math.toRadians(this.phasevibration)),
                this.magvibration * sin(Math.toRadians(this.phasevibration)));
    }

    public Complex getComplexWeight() {
        return new Complex(this.magweight * cos(Math.toRadians(this.phaseweight)),
                this.magweight * sin(Math.toRadians(this.phaseweight)));
    }

    public Boolean getStage() {
        if (this.stage == null) {
            setStage(false);
            return this.stage;
        } else {
            return this.stage;
        }
    }

    public Double getMagTotalWeight() {
        return roundAvoid(getComplexTotalWeight().abs(), 2);
    }

    public Double getPhaseTotalWeight() {
        return roundAvoid(getComplexTotalWeight().phase(), 2);
    }

    public Double getMagTotalVibration() {
        return roundAvoid(getComplexTotalVibration().abs(), 2);
    }

    public Double getPhaseTotalVibration() {
        return roundAvoid(getComplexTotalVibration().phase(), 2);
    }

    private Double roundAvoid(Double value, Integer places) {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}

