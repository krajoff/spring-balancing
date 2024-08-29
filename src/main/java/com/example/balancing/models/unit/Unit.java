package com.example.balancing.models.unit;

import com.example.balancing.models.station.Station;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;
import java.util.Date;

/**
 * Сущность агрегата. Представляет данные о конкретном агрегате,
 * включая его номер, тип, точности измерения вибрации и грузов,
 * описание, станцию, к которой он относится, а также метаданные,
 * такие как даты создания и обновления.
 */
@Entity(name = "Unit")
@Table(name = "units")
@EqualsAndHashCode
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Unit {

    /**
     * Уникальный идентификатор агрегата.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * Номер агрегата. Значение по умолчанию — 1.
     * Максимальная длина — 3 цифры.
     */
    @Column(name = "unit_number", columnDefinition = "integer default 1")
    @Size(max = 3)
    private Integer unitNumber;

    /**
     * Тип агрегата. Значение по умолчанию — 'No name type'.
     * Максимальная длина — 50 символов.
     */
    @Column(name = "type",
            columnDefinition = "varchar(50) default 'No name type'")
    @Size(max = 50)
    private String type;

    /**
     * Количество знаков после запятой при отображении значений грузов.
     * Максимальное значение — 2 знака.
     */
    @Column(name = "weight_precision")
    @Size(max = 2)
    private Integer weightPrecision;

    /**
     * Единица измерения грузов (г, кг, т). Максимальная длина — 2 символа.
     */
    @Column(name = "weight_unit_measure")
    @Size(max = 2)
    private String weightUnitMeasure;

    /**
     * Количество знаков после запятой при отображении значений вибрации.
     * Максимальное значение — 2 знака.
     */
    @Column(name = "vibration_precision")
    @Size(max = 2)
    private Integer vibrationPrecision;

    /**
     * Дополнительное описание.
     */
    @Column(name = "description")
    @Size(max = 255)
    private String description;

    /**
     * Список грузов, относящихся к агрегату.
     * Отношение один ко многим с каскадными операциями удаления и вставки.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Weight> weights;

    /**
     * Станция, с которой связан агрегат.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station station;

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

}
