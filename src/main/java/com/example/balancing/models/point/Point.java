package com.example.balancing.models.point;

import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Сущность точки измерения вибрации. Содержит название точки,
 * список связанных записей, id связанного агрегата, а также
 * метаданные, такие как даты создания и обновления.
 */
@Entity(name = "Point")
@Table(name = "points")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Point {

    /**
     * Уникальный идентификатор точки измерения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude private Long id;

    /**
     * Название точки измерения. Обязательное поле.
     * Максимальная длина — 10 символов.
     */
    @Column(name = "name", nullable = false,
            columnDefinition = "varchar(10) default 'ГП'")
    @Size(max = 10)
    @NonNull private String name;

    /**
     * Связанный агрегат.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @NonNull private Unit unit;

    /**
     * Список записей вибрации, связанных с данной точкой.
     * Отношение один ко многим с каскадными операциями.
     */
    @OneToMany(mappedBy = "point", cascade = {CascadeType.REMOVE, CascadeType.REFRESH},
             orphanRemoval = true)
    @ToString.Exclude private List<Record> records;

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

}

