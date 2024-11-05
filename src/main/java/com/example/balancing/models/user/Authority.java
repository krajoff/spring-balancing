package com.example.balancing.models.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Authority")
@Table(name = "authorities")
@Data
public class Authority {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    /**
     * Связь с идентификатором пользователем.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Строковое представление роли: ROLE_MODERATOR, ROLE_USER, DELETED_ACCOUNT ...
     */
    @Column(name = "authority")
    private String authority;

}
