package com.example.balancing.entity.mode;

import com.example.balancing.entity.record.Record;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

/**
 * Сущность режима работы агрегата: 100%n, 100%U, 50 МВт и т.д.
 * Содержит название, список записей, выполненных в данном режиме.
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "Mode")
@Table(name = "modes")
public class Mode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Size(max = 7)
    private String name;

    @OneToMany(mappedBy = "mode", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<Record> records;

    @CreationTimestamp
    @Column(updatable = false, name = "created_on")
    private Date createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private Date updatedOn;

    @Version
    @Column(name = "version")
    private Long version = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mode mode)) return false;

        if (!getName().equals(mode.getName())) return false;
        return getVersion() != null ? getVersion()
                .equals(mode.getVersion()) : mode.getVersion() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        return result;
    }

}
