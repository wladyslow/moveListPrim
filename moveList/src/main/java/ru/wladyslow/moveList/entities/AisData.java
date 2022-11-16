package ru.wladyslow.moveList.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "AIS_DATA")
public class AisData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long vesselId;

    @OneToOne(mappedBy = "aisData")
    private Vessel vessel;

    @Column
    private String etaDestination;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime etaDateAndTime;

    @Column
    private String navigationStatus;

    @Column
    private double currentDraft;

    @Column
    private double currentSpeed;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime lastUpdated;

    public AisData(Long vesselId, Vessel vessel, String etaDestination,
                   LocalDateTime etaDateAndTime, String navigationStatus,
                   double currentDraft, double currentSpeed,
                   LocalDateTime lastUpdated) {
        this.vesselId =vesselId;
        this.vessel = vessel;
        this.etaDestination = etaDestination;
        this.etaDateAndTime = etaDateAndTime;
        this.navigationStatus = navigationStatus;
        this.currentDraft = currentDraft;
        this.currentSpeed = currentSpeed;
        this.lastUpdated = lastUpdated;
    }
}
