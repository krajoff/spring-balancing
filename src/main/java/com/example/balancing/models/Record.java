package com.example.balancing.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Entity
@Table(name = "records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements IRecord{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mode")
    private String mode;
    @Column(name = "magvibration")
    private Double magvibration;
    @Column(name = "phasevibration")
    private Double phasevibration;
    @Column(name = "magweight")
    private Double magweight;
    @Column(name = "phaseweight")
    private Double phaseweight;
    @Column(name = "reference", columnDefinition = "bigint default -1")
    private Long reference;
    @Column(name = "stage")
    private boolean stage;

    public Complex getComplexVibration() {
        return new Complex(this.magvibration * cos(Math.toRadians(this.phasevibration)),
                this.magvibration * sin(Math.toRadians(this.phasevibration)));
    }

    public Complex getComplexWeight() {
        return new Complex(this.magweight * cos(Math.toRadians(this.phaseweight)),
                this.magweight * sin(Math.toRadians(this.phaseweight)));
    }


}

