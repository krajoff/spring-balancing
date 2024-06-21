package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.mode.Mode;
import com.example.balancing.models.place.Place;
import com.example.balancing.models.weight.Weight;
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
    @Column(name = "id", nullable = false)
    @NonNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @NonNull
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id")
    private Mode mode;

    @Column(name = "mag_vibration", nullable = false)
    @NonNull
    private Double magVibration;

    @Column(name = "phase_vibration", nullable = false)
    @NonNull
    private Double phaseVibration;

    @Transient
    private Complex complexVibration;

    @Getter
    @Column(name = "stage", columnDefinition = "boolean default true")
    @NonNull
    private Boolean stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @Column(name = "is_manual_sensitivity",
            columnDefinition = "boolean default false")
    @NonNull
    private Boolean isManualSensitivity;

    @Column(name = "mag_sensitivity")
    private Double magSensitivity;

    @Column(name = "phase_sensitivity")
    private Double phaseSensitivity;

    @Transient
    private Complex complexSensitivity;

    @Transient
    private Weight targetWeight;

    public Complex getComplexVibration() {
        return new Complex(this.magVibration *
                cos(Math.toRadians(this.phaseVibration)),
                this.magVibration *
                        sin(Math.toRadians(this.phaseVibration)));
    }

    public void setPhaseSensitivity(Double phaseSensitivity) {
        this.phaseSensitivity = Math.toDegrees(phaseSensitivity);
    }

    public Complex getComplexSensitivity() {
        return new Complex(this.magSensitivity *
                cos(Math.toRadians(this.phaseSensitivity)),
                this.magSensitivity * sin(Math.toRadians(this.phaseSensitivity)));
    }

    public Double getMagSensitivity() {
        return getComplexSensitivity().abs();
    }

    public Double getPhaseSensitivity() {
        return roundAvoid(Math
                .toDegrees(getComplexSensitivity()
                        .phase()), 0);
    }

    private Double roundAvoid(Double value, Integer places) {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public Record getRecord() {
        return this;
    }
}

