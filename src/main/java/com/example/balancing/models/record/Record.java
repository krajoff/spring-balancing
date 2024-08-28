package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.mode.Mode;
import com.example.balancing.models.point.Point;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import lombok.*;

import static java.lang.Math.*;

/**
 * Сущность вибрации в точке измерения. Содержит место измерение,
 * режим, значения амплитуды и фазы в deg (комплексное значение инкапсулиется
 * и вычисляется только при чтении), флаг участия в общем оптимизационном
 * расчете, связанный балансировочный груз, значения амплитуды и
 * фазы в deg чувствительности (комплексное значение инкапсулиется
 * и вычисляется только при чтении), а также балансировочный
 * груз компенсирующий данную вибрацию.
 */

@Entity
@Table(name = "records")
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Record implements IRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id")
    private Mode mode;

    @Column(name = "mag_vibration", nullable = false)
    private Double magVibration;

    @Column(name = "phase_vibration", nullable = false)
    private Double phaseVibration;

    @Transient
    private Complex complexVibration;

    @Getter
    @Column(name = "stage", columnDefinition = "boolean default true")
    private Boolean stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id", nullable = false)
    private Weight weight;

    @Column(name = "is_manual_sensitivity",
            columnDefinition = "boolean default false")
    private Boolean isManualSensitivity;

    @Column(name = "mag_sensitivity")
    private Double magSensitivity;

    @Column(name = "phase_sensitivity")
    private Double phaseSensitivity;

    @Transient
    private Complex complexSensitivity;

    @Transient
    private Weight targetWeight;

    @PrePersist
    void prePersist() {
        if (this.stage == null)
            this.stage = true;
    }


    public Complex getComplexVibration() {
        return new Complex(this.magVibration *
                cos(Math.toRadians(this.phaseVibration)),
                this.magVibration *
                        sin(Math.toRadians(this.phaseVibration)));
    }

    public Complex getComplexSensitivity() {
        return new Complex(this.magSensitivity *
                cos(Math.toRadians(this.phaseSensitivity)),
                this.magSensitivity *
                        sin(Math.toRadians(this.phaseSensitivity)));
    }

    public Record getRecord() {
        return this;
    }
}

