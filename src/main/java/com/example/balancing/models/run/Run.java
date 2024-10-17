package com.example.balancing.models.run;

import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Run")
@Table(name = "runs")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Run {

    /**
     * Уникальный идентификатор записи вибрации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @ToString.Exclude
    private Long id;

    /**
     * Порядковый номер пуска
     */
    @Column(name = "number")
    private Integer number;

    /**
     * Агрегат, связанный с пуском
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    /**
     * Связь пуска с плоскостью. Связь многие пуски к одной плоскости.
     * Один пуск не может быть связан с несколькими плоскостями.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private Plane plane;

    /**
     * Связь пуска и груза, который добавлен в этот пуск
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id", referencedColumnName = "id")
    private Weight weight;

    /**
     * Ссылка на предыдущий пуск
     */
    @Column(name = "reference_run_id")
    private Long referenceRunId;

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

    public void addWeight(Weight weight){
         plane.addWeight(weight);
    }

    public void removeWeight(Weight weight){
        plane.removeWeight(weight);
    }

    public Run getRun(){
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Run run)) return false;

        if (!getNumber().equals(run.getNumber())) return false;
        if (!getUnit().equals(run.getUnit())) return false;
        if (!getPlane().equals(run.getPlane())) return false;
        if (getWeight() != null ? !getWeight().equals(run.getWeight()) : run.getWeight() != null) return false;
        return getReferenceRunId() != null ? getReferenceRunId().equals(run.getReferenceRunId()) : run.getReferenceRunId() == null;
    }

    @Override
    public int hashCode() {
        int result = getNumber().hashCode();
        result = 31 * result + getUnit().hashCode();
        result = 31 * result + getPlane().hashCode();
        result = 31 * result + (getWeight() != null ? getWeight().hashCode() : 0);
        result = 31 * result + (getReferenceRunId() != null ? getReferenceRunId().hashCode() : 0);
        return result;
    }
}
