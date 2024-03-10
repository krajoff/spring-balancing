package com.example.balancing.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "login")
    private String login;

    @NotBlank
    @Transient
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "role")
    private Role role;

    @NotBlank
    @Column(name = "email")
    private String email;
}
