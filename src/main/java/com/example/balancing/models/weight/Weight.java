package com.example.balancing.models.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Сущность балансировочного и расчетного грузов.
 * <p>
 * Этот класс содержит информацию о плоскости, номере пуска,
 * ссылке на референсный груз (если он есть), и других атрибутах веса.
 * </p>
 * <p>
 * Включает методы для получения и установки различных параметров веса,
 * а также расчета комплексного веса на основе магнитного и фазового значений.
 */
@Entity(name = "Weight")
@Table(name = "weights")
@EqualsAndHashCode
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Weight {

    /**
     * Идентификатор веса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Плоскость установки груза.
     */
    @Column(name = "plane", nullable = false)
    @NonNull private Plane plane;

    /**
     * Номер пуска. Не может быть null, по умолчанию равен 0.
     */
    @Column(name = "run", nullable = false,
            columnDefinition = "integer default 0")
    @NonNull private Integer run;

    /**
     * Ссылка на референсный груз. По умолчанию равен -1, т.е. без ссылки.
     */
    @Column(name = "reference", columnDefinition = "integer default -1")
    @NonNull private Integer reference;

    /**
     * Значение веса. По умолчанию равно 0.
     */
    @Column(name = "mag_weight", columnDefinition = "double default 0")
    @NonNull private Double magWeight;

    /**
     * Значение фазы веса. По умолчанию равно 0.
     */
    @Column(name = "phase_weight", columnDefinition = "double default 0")
    @NonNull private Double phaseWeight;

    /**
     * Вес в комплексных числах. Не сохраняется в базе данных.
     */
    @Transient
    private Complex complexWeight;

    /**
     * Связь c агрегатом.
      */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    /**
     * Список записей вибрации, связанных с этим весом.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "weight",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;

    /**
     * Системная информация о весе.
     */
    @Column(name = "system_information")
    private String systemInformation;

    /**
     * Флаг, указывающий, является ли данный вес целевым.
     * По умолчанию равен false.
     */
    @Column(name = "is_target", columnDefinition = "boolean default false")
    private boolean isTarget;

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




