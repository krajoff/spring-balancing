package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import lombok.*;

import static java.lang.Math.*;

@Entity
@Table(name = "records")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Record implements IRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NonNull
    private Long id;


    @Column(name = "point_id", nullable = false)
    @OneToOne
    private Integer pointId;

    @Column(name = "inside_id")
    @NonNull
    private String name;



    @Column(name = "mode", nullable = false)
    @NonNull
    private String mode;

    @Column(name = "mag_vibration", nullable = false)
    @NonNull
    private Double magVibration;

    @Column(name = "phase_vibration", nullable = false)
    @NonNull
    private Double phaseVibration;

    @Getter
    @Column(name = "stage", columnDefinition = "boolean default true")
    @NonNull
    private Boolean stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    @Transient
    private Complex complexVibration;

    public Complex getComplexVibration() {
        return new Complex(this.magVibration *
                cos(Math.toRadians(this.phaseVibration)),
                this.magVibration *
                        sin(Math.toRadians(this.phaseVibration)));
    }
}

