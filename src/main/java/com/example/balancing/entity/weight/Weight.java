package com.example.balancing.entity.weight;

import com.example.balancing.vo.complex.Complex;
import com.example.balancing.entity.plane.Plane;
import com.example.balancing.entity.run.Run;
import com.example.balancing.entity.record.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Entity(name = "Weight")
@Table(name = "weights")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Weight implements IWeight {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    private Plane plane;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", referencedColumnName = "id")
    private Run run;

    @Column(name = "mag_weight", columnDefinition = "double default 0")
    private Double magWeight;

    @Column(name = "phase_weight", columnDefinition = "double default 0")
    private Double phaseWeight;

    @Transient
    private Complex complexWeight;

    @OneToMany(mappedBy = "weight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    @Column(name = "system_information")
    @Size(max = 255)
    private String systemInformation;

    @Column(name = "is_target", nullable = false, columnDefinition = "boolean default false")
    private Boolean isTarget;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Version
    @Column(name = "version")
    private Long version = 1L;


    public Complex getComplexWeight() {
        return new Complex(this.magWeight *
                cos(Math.toRadians(this.phaseWeight)),
                this.magWeight * sin(Math.toRadians(this.phaseWeight)));
    }

    public Weight getWeight() {
        return this;
    }

}




