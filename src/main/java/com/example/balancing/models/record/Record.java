package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.mode.Mode;
import com.example.balancing.models.point.Point;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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

@Entity(name = "Record")
@Table(name = "records")
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Record implements IRecord {

    /**
     * Уникальный идентификатор записи вибрации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Точка измерения вибрации.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", referencedColumnName = "id")
    private Point point;

    /**
     * Режим работы, при котором проводится измерение вибрации.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id", referencedColumnName = "id")
    private Mode mode;

    /**
     * Амплитуда вибрации.
     */
    @Column(name = "mag_vibration", nullable = false,
            columnDefinition = "double default 0")
    private Double magVibration;

    /**
     * Фаза вибрации в градусах.
     */
    @Column(name = "phase_vibration", nullable = false,
            columnDefinition = "double default 0")
    private Double phaseVibration;

    /**
     * Комплексное значение вибрации, рассчитываемое на основе амплитуды и фазы.
     * Не хранится в базе данных.
     */
    @Transient
    private Complex complexVibration;

    /**
     * Флаг участия в общем оптимизационном расчете.
     * Значение по умолчанию — true.
     */
    @Getter
    @Column(name = "is_used", columnDefinition = "boolean default true")
    private Boolean isUsed;

    /**
     * Балансировочный груз, связанный с вибрацией.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id", nullable = false,
            referencedColumnName = "id")
    private Weight weight;

    /**
     * Флаг ручной настройки чувствительности.
     * Значение по умолчанию — false.
     */
    @Column(name = "is_manual_sensitivity",
            columnDefinition = "boolean default false")
    private Boolean isManualSensitivity;

    /**
     * Амплитуда чувствительности вибрации.
     */
    @Column(name = "mag_sensitivity")
    private Double magSensitivity;

    /**
     * Фаза чувствительности вибрации в градусах.
     */
    @Column(name = "phase_sensitivity")
    private Double phaseSensitivity;

    /**
     * Комплексное значение чувствительности,
     * рассчитываемое на основе амплитуды и фазы.
     * Не хранится в базе данных.
     */
    @Transient
    private Complex complexSensitivity;

    /**
     * Дата создания. Поле автоматически заполняется
     * при создании и не может быть обновлено.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    /**
     * Дата последнего обновления. Поле автоматически
     * обновляется при изменении записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * Версия.
     */
    @Version
    @Builder.Default
    @Column(name = "version")
    private Long version = 1L;

    @PrePersist
    void prePersist() {
        if (this.isUsed == null)
            this.isUsed = true;
    }

    /**
     * Возвращает комплексное значение вибрации, вычисленное на основе
     * амплитуды и фазы.
     *
     * @return Комплексное значение вибрации.
     */
    public Complex getComplexVibration() {
        return new Complex(this.magVibration *
                cos(Math.toRadians(this.phaseVibration)),
                this.magVibration *
                        sin(Math.toRadians(this.phaseVibration)));
    }

    /**
     * Возвращает комплексное значение чувствительности, вычисленное на основе
     * амплитуды и фазы.
     *
     * @return Комплексное значение чувствительности.
     */
    public Complex getComplexSensitivity() {
        return new Complex(this.magSensitivity *
                cos(Math.toRadians(this.phaseSensitivity)),
                this.magSensitivity *
                        sin(Math.toRadians(this.phaseSensitivity)));
    }

    /**
     * Возвращает текущую запись.
     *
     * @return Объект Record.
     */
    public Record getRecord() {
        return this;
    }
}

