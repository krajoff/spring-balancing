package com.example.balancing.models.unit;

import com.example.balancing.models.mode.Mode;
import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.point.Point;
import com.example.balancing.models.run.Run;
import com.example.balancing.models.station.Station;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Сущность агрегата. Представляет данные о конкретном агрегате,
 * включая его номер, тип, точности измерения вибрации и грузов,
 * описание, станцию, к которой он относится, а также метаданные,
 * такие как даты создания и обновления.
 */
@Entity(name = "Unit")
@Table(name = "units")
@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    /**
     * Уникальный идентификатор агрегата.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * Номер агрегата на станции. Значение по умолчанию — 1.
     * Максимальная длина — 3 цифры.
     */
    @Column(name = "unit_number")
    @Size(max = 3)
    private Integer unitNumber;

    /**
     * Тип агрегата. Значение по умолчанию — 'Без типа'.
     * Максимальная длина — 50 символов.
     */
    @Column(name = "type")
    @Size(max = 50)
    private String type;

    /**
     * Список пусков, относящихся к агрегату.
     */
    @OneToMany(mappedBy = "unit",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs = new ArrayList<>();

    /**
     * Список плоскостей, относящихся к агрегату. Односторонняя связь.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plane> planes = new ArrayList<>();

    /**
     * Список режимов, относящихся к агрегату.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mode> modes = new ArrayList<>();

    /**
     * Список точек измерения вибрации, относящихся к агрегату.
     */
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points;

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
    @Size(max = 5)
    private String weightUnitMeasure;

    /**
     * Количество знаков после запятой при отображении значений вибрации.
     * Максимальное значение — 2 знака.
     */
    @Column(name = "vibration_precision")
    @Size(max = 2)
    private Integer vibrationPrecision;

    /**
     * Единица измерения вибрации (мкм, мм/с). Максимальная длина — 5 символа.
     */
    @Column(name = "vibration_unit_measure")
    @Size(max = 5)
    private String vibrationUnitMeasure;

    /**
     * Дополнительное описание.
     */
    @Column(name = "description")
    @Size(max = 255)
    private String description;

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
    @Column(name = "version")
    @EqualsAndHashCode.Exclude
    private Long version = 1L;

}
