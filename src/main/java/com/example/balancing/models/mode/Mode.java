package com.example.balancing.models.mode;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Название режима. Максимальная длина — 10 символов.
     */
    @Column(name = "name", columnDefinition = "varchar(10) default 'no mode'")
    @Size(max = 10)
    @NonNull private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mode",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mode_id")
    @ToString.Exclude
    private List<Record> records;

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
