package com.example.balancing.models.user;

import com.example.balancing.models.station.Station;
import com.example.balancing.models.token.RefreshToken;
import com.example.balancing.models.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Сущность пользователя. Хранит информацию о пользователе системы,
 * включая его учетные данные, роль, и связанный список станций.
 */
@Entity(name = "User")
@Table(name = "users")
@Builder
@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "user_entity-graph",
        attributeNodes = @NamedAttributeNode("stations"))
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    /**
     * Имя пользователя. Должно быть уникальным и не может быть пустым.
     */
    @Column(name = "username", unique = true, nullable = false)
    @Size(max = 50)
    private String username;

    /**
     * Пароль пользователя. Не может быть пустым.
     */
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Подтверждение пароля. Это временное поле и не сохраняется в базе данных.
     */
    @Transient
    private String passwordConfirm;

    /**
     * Роль пользователя в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Электронная почта пользователя. Должна быть уникальной.
     */
    @Email
    @Size(max = 255)
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Приоритет пользователя. Опциональное поле.
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * Рефреш-токен.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    /**
     * Список станций, связанных с данным пользователем.
     * Отношение один ко многим с каскадными операциями и
     * удалением орфанных записей.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Station> stations = new ArrayList<>();

    /**
     * Дата создания учетной записи пользователя. Поле автоматически заполняется
     * при создании и не может быть обновлено.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @EqualsAndHashCode.Exclude
    private Date createdAt;

    /**
     * Дата последнего обновления учетной записи пользователя.
     * Поле автоматически обновляется при каждом изменении записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    @EqualsAndHashCode.Exclude
    private Date updatedAt;

    public void addStation(Station station){
        this.stations.add(station);
    }

    public void removeStation(Station station){
        this.stations.remove(station);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority =
                new SimpleGrantedAuthority(role.name());
        return List.of(grantedAuthority);
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
