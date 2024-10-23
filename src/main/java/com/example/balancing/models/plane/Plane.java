package com.example.balancing.models.plane;

import com.example.balancing.models.run.Run;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

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
    private Long id;

    /**
     * Номер плоскости. Максимальная длина — 1 символов.
     */
    @Column(name = "number", nullable = false,
            columnDefinition = "integer default 1")
    @Size(max = 1)
    @NonNull
    private Integer number;

    /**
     * Связь плоскости со всеми пусками и грузами
     */
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "plane_run_weight",
//            joinColumns = {@JoinColumn(name = "plane_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "weight_id", referencedColumnName = "id")})
//    @MapKeyJoinColumn(name = "run_id")
    private Map<Run, Weight> runWeightMap = new HashMap<>();

    /**
     * Список грузов, связанных с данной плоскостью.
     * Отношение один ко многим с каскадными операциями
     * и удалением орфанных записей.
     */
    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Weight> weights = new ArrayList<>();

    /**
     * Список расчетных балансировочных грузов, связанных с
     * данной плоскостью. Они не хранятся в базе данных и вычисляются каждый раз
     * при обращении.
     */
    @Transient
    private List<Weight> targetWeights = new ArrayList<>();

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

    public void addWeight(Weight weight) {
        weights.add(weight);
        weight.setPlane(this);
    }

    public void removeWeight(Weight weight) {
        weights.remove(weight);
        weight.setPlane(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane plane)) return false;

        if (!getNumber().equals(plane.getNumber())) return false;
        return getRunWeightMap() != null ? getRunWeightMap().equals(plane.getRunWeightMap()) : plane.getRunWeightMap() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber().hashCode();
        result = 31 * result + (getRunWeightMap() != null ? getRunWeightMap().hashCode() : 0);
        return result;
    }
}
