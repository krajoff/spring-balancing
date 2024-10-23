package com.example.balancing.models.point;

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

/**
 * Сущность точки измерения вибрации. Содержит название точки,
 * список связанных записей, id связанного агрегата, а также
 * метаданные, такие как даты создания и обновления.
 */
@Entity(name = "Point")
@Table(name = "points")
@Getter
@Setter
@ToString
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
    @ToString.Exclude private Long id;

    /**
     * Название точки измерения. Обязательное поле.
     * Максимальная длина — 10 символов.
     */
    @Column(name = "name", nullable = false)
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
    @OneToMany(mappedBy = "point", cascade = CascadeType.ALL,
             orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

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
    @Column(name = "version")
    private Long version = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        if (!getName().equals(point.getName())) return false;
        return getUnit().equals(point.getUnit());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getUnit().hashCode();
        return result;
    }
}

