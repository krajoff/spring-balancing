package com.example.balancing.models.run;

import com.example.balancing.models.plane.Plane;
import com.example.balancing.models.unit.Unit;
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
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Run {

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
     * Номер порядкового пуска
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
     * Связь пусков со всеми плоскостями
     */
    @ManyToMany()
    @JoinTable(name = "run_plane",
            joinColumns = {@JoinColumn(name = "fk_run")},
            inverseJoinColumns = {@JoinColumn(name = "fk_plane")})
    private Set<Plane> planes = new HashSet<Plane>();

    /**
     * Ссылка на предыдущий пуск
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_run_id")
    private Run referenceRun;

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
