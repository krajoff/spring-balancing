package com.example.balancing.models.target;

import com.example.balancing.models.complex.Complex;
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
    @Column(name = "magtargetweight", nullable = false)
    private Double magtargetweight;
    @Column(name = "phasetargetweight", nullable = false)
    private Double phasetargetweight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
