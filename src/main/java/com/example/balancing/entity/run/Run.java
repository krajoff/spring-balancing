package com.example.balancing.entity.run;

import com.example.balancing.entity.Record;
import com.example.balancing.entity.Unit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name = "Run")
@Table(name = "runs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Run {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_number")
    private Integer runNumber;

    @Column(name = "reference_run_id")
    private Long referenceRunId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column
    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "run_parameters")
    private RunParameter runsParameters;

    @CreationTimestamp
    @Column(updatable = false, name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Version
    @Column(name = "version")
    private Long version = 1L;

}
