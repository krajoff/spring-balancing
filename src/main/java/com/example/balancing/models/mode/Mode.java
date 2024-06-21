package com.example.balancing.models.mode;

import com.example.balancing.models.place.Place;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "mode")
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class Mode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mode_number")
    private Integer modeNumber;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mode",
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mode_id")
    private List<Record> records;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

}
