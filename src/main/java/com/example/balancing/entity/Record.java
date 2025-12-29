package com.example.balancing.entity;

import com.example.balancing.entity.run.Run;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Record")
@Table(name = "records")
public class Record {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    @Column(name = "point_name")
    private String pointName;

    @Column(name = "mode_name")
    private String modeName;

    @Column(name = "mag_vibration", nullable = false)
    private Double magVibration;

    @Column(name = "phase_vibration", nullable = false)
    private Double phaseVibration;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "is_manual_sensitivity")
    private Boolean isManualSensitivity;

    @Column(name = "mag_sensitivity")
    private Double magSensitivity;

    @Column(name = "phase_sensitivity")
    private Double phaseSensitivity;

    @CreationTimestamp
    @Column(updatable = false, name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_On")
    private LocalDateTime updatedOn;

    @Version
    @Column(name = "version")
    private Long version = 1L;

    @PrePersist
    void prePersist() {
        if (this.isUsed == null)
            this.isUsed = true;
    }

}

