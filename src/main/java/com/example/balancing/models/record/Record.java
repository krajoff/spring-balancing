package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
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
    @Column(name = "id")
    @NonNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    @NonNull
    private Integer pointId;

    @Column(name = "mode", nullable = false)
    @NonNull
    private String mode;

    @Column(name = "mag_vibration", nullable = false)
    @NonNull
    private Double magVibration;

    @Column(name = "phase_vibration", nullable = false)
    @NonNull
    private Double phaseVibration;

    @Column(name = "is_manual_sensitivity", columnDefinition = "boolean default false")
    @NonNull
    private Boolean isManualSensitivity;

    @Column(name = "mag_sensitivity")
    private Double magSensitivity;

    @Column(name = "phase_sensitivity")
    private Double phaseSensitivity;

    @Getter
    @Column(name = "stage", columnDefinition = "boolean default true")
    @NonNull
    private Boolean stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @Transient
    private Complex complexVibration;

    @Transient
    private Complex complexSensitivity;

    public Complex getComplexVibration() {
        return new Complex(this.magVibration *
                cos(Math.toRadians(this.phaseVibration)),
                this.magVibration *
                        sin(Math.toRadians(this.phaseVibration)));
    }
}

