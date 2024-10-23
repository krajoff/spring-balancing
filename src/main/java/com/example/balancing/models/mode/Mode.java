package com.example.balancing.models.mode;

import com.example.balancing.models.record.Record;
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
    @ToString.Exclude private Long id;

    /**
     * Название режима. Максимальная длина — 10 символов.
     */
    @Column(name = "name")
    @Size(max = 20)
    @NonNull private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mode mode)) return false;

        if (!getName().equals(mode.getName())) return false;
        return getVersion() != null ? getVersion()
                .equals(mode.getVersion()) : mode.getVersion() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        return result;
    }
}
