package com.example.balancing.models.mode;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="mode")
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class Mode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="mode_number")
    private Integer modeNumber;

    @Column(name="name")
    private String name;

    @JoinColumn(name = "")

}
