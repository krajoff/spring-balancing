package com.example.balancing.models.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Authority")
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "authority")
    private String authority;

}
