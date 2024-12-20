package com.example.balancing.models.token;

import com.example.balancing.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "RefreshToken")
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class RefreshToken {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private long id;

    /**
     * Пользователь связанный с рефреш-токеном.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Токен
     */
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Время действия рефреш-токена.
     */
    @Column(name = "expiration", nullable = false)
    private Date expiration;

}

