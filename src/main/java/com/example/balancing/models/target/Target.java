package com.example.balancing.models.target;

import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "targets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Target implements ITarget{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "mag_weight", nullable = false)
    private Double magWeight;

    @Column(name = "phase_weight", nullable = false)
    private Double phaseWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
