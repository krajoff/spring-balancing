package com.example.balancing.models.mode;

import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Сущность режима работы агрегата: 100%n, 100%U, 50 МВт и т.д.
 * Содержит название, список записей, выполненных в данном режиме.
 */
@Entity(name = "Mode")
@Table(name = "modes")
@EqualsAndHashCode
@Builder
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Mode {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Название режима. Максимальная длина — 10 символов.
     */
    @Column(name = "name", columnDefinition = "varchar(20) default 'no mode'")
    @Size(max = 20)
    @NonNull private String name;

    /**
     * Ссылка на агрегат.
     */
    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @NonNull private Unit unit;

    /**
     * Список записей вибрации, связанных с данным режимом.
     */
    @OneToMany(mappedBy = "mode", cascade = {CascadeType.REFRESH, CascadeType.MERGE,
    CascadeType.DETACH})
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
