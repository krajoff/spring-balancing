package com.example.balancing.entity.run;

import lombok.Data;

import java.io.Serializable;

@Data
public class RunParameters implements Serializable {
    private Integer mag_weight;
    private Integer phase_weight;
    private Integer num_plane;
}
