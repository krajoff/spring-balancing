package com.example.balancing.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @Size(min = 5, message = "At least 5 characters")
    private String username;

    @Size(min = 5, message = "At least 5 characters")
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "roles")
    private String roles;

    @NotEmpty
    @Email
    @Size(max = 255)
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "priority")
    private Integer priority;

}
