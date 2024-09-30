package com.example.balancing.models.plane;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Сущность плоскости установки груза, как реального, так и вычисленного.
 * Содержит информацию о названии места (ротор, верхний вентилятор,
 * сторона возбудителя, просто цифра и т.д.) и список грузов в данной
 * плоскости агрегата
 */
@Entity(name = "Plane")
@Table(name = "planes")
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Plane {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude private Long id;

    /**
     * Номер плоскости. Максимальная длина — 1 символов.
     */
    @Column(name = "number", nullable = false,
            columnDefinition = "integer default 1")
    @Size(max = 1)
    @NonNull private Integer number;

    /**
     * Список грузов, связанных с данной плоскостью.
     * Отношение один ко многим с каскадными операциями
     * и удалением орфанных записей.
     */
    @OneToMany(mappedBy = "plane", cascade = {CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true)
    @ToString.Exclude private List<Weight> weights;

    /**
     * Ссылка на агрегат.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

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
