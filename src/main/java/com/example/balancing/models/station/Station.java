package com.example.balancing.models.station;

import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Сущность станции. Представляет данные о станции (ГЭС, АЭС, ГРЭС и т.д.).
 * Содержит только название станции и список агрегатов,
 * относящихся к данной станции.
 */
@Entity(name = "Station")
@Table(name = "stations")
@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@NamedEntityGraph(name = "station_entity-graph",
        attributeNodes = @NamedAttributeNode("units"))
public class Station {

    /**
     * Уникальный идентификатор станции.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Название станции. Значение по умолчанию — 'Без станции'.
     * Максимальная длина — 50 символов.
     */
    @Column(name = "name")
    @Size(max = 50)
    @NonNull
    private String name;

    /**
     * Список агрегатов, привязанных к станции. Агрегаты каскадно удаляются
     * при удалении станции.
     */
    @OneToMany(mappedBy = "station",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unit> units = new ArrayList<>();

    /**
     * Пользователь, с которым связана станция.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
