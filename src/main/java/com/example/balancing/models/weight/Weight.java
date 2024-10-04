package com.example.balancing.models.weight;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.run.Run;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.record.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
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
public class Weight implements IWeight {

    /**
     * Уникальный идентификатор веса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Плоскость установки груза.
     */
    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private Plane plane;

    /**
     * Ссылка на пуск.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", referencedColumnName = "id")
    private Run run;

    /**
     * Значение массы груза. По умолчанию равно 0.
     */
    @Column(name = "mag_weight", columnDefinition = "double default 0")
    @NonNull
    private Double magWeight;

    /**
     * Значение фазы груза. По умолчанию равно 0.
     */
    @Column(name = "phase_weight", columnDefinition = "double default 0")
    @NonNull
    private Double phaseWeight;

    /**
     * Груз в комплексных числах. Не сохраняется в базе данных.
     */
    @Transient
    private Complex complexWeight;

    /**
     * Значение общей массы груза. Не сохраняется в базе данных.
     */
    @Transient
    private Double magTotalWeight;

    /**
     * Значение фазы груза. Не сохраняется в базе данных.
     */
    @Transient
    private Double phaseTotalWeight;

    /**
     * Общий груз в комплексных числах. Не сохраняется в базе данных.
     */
    @Transient
    private Complex complexTotalWeight;

    /**
     * Список записей вибрации, связанных с этим весом.
     */
    @OneToMany(mappedBy = "weight", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    /**
     * Системная информация о весе.
     */
    @Column(name = "system_information")
    @Size(max = 255)
    @NonNull
    private String systemInformation;

    /**
     * Флаг, указывающий, является ли данный вес целевым.
     * По умолчанию равен false.
     */
    @Column(name = "is_target", nullable = false,
            columnDefinition = "boolean default false")
    @NonNull
    private Boolean isTarget;

    /**
     * Дата создания. Поле автоматически заполняется
     * при создании и не может быть обновлено.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @EqualsAndHashCode.Exclude
    private Date createdAt;

    /**
     * Дата последнего обновления. Поле автоматически
     * обновляется при изменении записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    @EqualsAndHashCode.Exclude
    private Date updatedAt;

    /**
     * Версия.
     */
    @Version
    @Builder.Default
    @Column(name = "version")
    @EqualsAndHashCode.Exclude
    private Long version = 1L;

    /**
     * Получение веса в комплексных числах на основе амплитуды и
     * фазового значений.
     *
     * @return Комплексный вес.
     */
    public Complex getComplexWeight() {
        return new Complex(this.magWeight *
                cos(Math.toRadians(this.phaseWeight)),
                this.magWeight * sin(Math.toRadians(this.phaseWeight)));
    }

    public Complex getComplexTotalWeight() {
        return new Complex(this.magTotalWeight *
                cos(Math.toRadians(this.phaseTotalWeight)),
                this.magTotalWeight *
                        sin(Math.toRadians(this.phaseTotalWeight)));
    }

    public Weight getWeight() {
        return this;
    }

}




