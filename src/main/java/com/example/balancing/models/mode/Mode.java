package com.example.balancing.models.mode;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность режима работы агрегата: 100%n, 100%U или 50 МВт
 * Содержит название, список записей, выполненные в этом режиме.
 */
@Entity
@Table(name = "mode")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Mode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mode",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mode_id")
    @ToString.Exclude
    private List<Record> records;

}
