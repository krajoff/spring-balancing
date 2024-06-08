package com.example.balancing.models.target;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "targets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Target implements ITarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mag_weight", nullable = false)
    private Double magWeight;

    @Column(name = "phase_weight", nullable = false)
    private Double phaseWeight;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;
}
