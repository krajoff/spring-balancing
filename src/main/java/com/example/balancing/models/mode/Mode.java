package com.example.balancing.models.mode;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность режима работы агрегата: 100%n, 100%U, 50 МВт и т.д.
 * Содержит название, список записей, выполненных в данном режиме.
 */
@Entity
@Table(name = "modes")
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
