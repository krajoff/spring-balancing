package com.example.balancing.models.unit;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.target.Target;
import com.example.balancing.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit implements IUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recordcount")
    private Long recordcount;

    @Column(name = "station")
    @Size(max = 50)
    private String station;

    @Column(name = "unitnumber")
    @Size(max = 50)
    private Integer unitnumber;

    @Column(name = "type")
    @Size(max = 50)
    private String type;

    @Column(name = "plate")
    @Size(max = 20)
    private Integer plate;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Target> targets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    void prePersist() {
        if (this.recordcount == null)
            this.recordcount = 0L;
    }

}
