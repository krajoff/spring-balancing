package com.example.balancing.models.place;

import com.example.balancing.models.weight.Weight;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "place")
@Data
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @Column(name = "place_id", nullable = false)
    private Integer placeId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "weight_id")
    private List<Weight> weights;

}
