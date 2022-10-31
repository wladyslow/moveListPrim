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
@Table(name = "MOVE")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "VESSEL_ID")
    private Vessel vessel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "POINT_OF_OPERATION_ID")
    private Point pointOfOperation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;

    @Column
    @DateTimeFormat(pattern = "HH:mm dd.MM.yy")
    private LocalDateTime timeAndDateOfOperation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "DESTINATION_POINT_ID")
    private Point destinationPoint;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "PILOT_ID")
    private Pilot pilot;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @Column
    private String operationAtBerth;

    @Column
    private Long callId;

    @Column
    private Long externalId;

    @Column
    private boolean isSent;

    public Move(Vessel vessel, Point pointOfOperation, Operation operation,
                LocalDateTime timeAndDateOfOperation, Point destinationPoint,
                Pilot pilot, String operationAtBerth, Long callId, Long externalId, boolean isSent) {
        this.vessel = vessel;
        this.pointOfOperation = pointOfOperation;
        this.operation = operation;
        this.timeAndDateOfOperation = timeAndDateOfOperation;
        this.destinationPoint = destinationPoint;
        this.pilot = pilot;
        this.operationAtBerth = operationAtBerth;
        this.callId = callId;
        this.externalId = externalId;
        this.isSent = isSent;
    }

    public Move(Vessel vessel, Point pointOfOperation,
                Operation operation, LocalDateTime timeAndDateOfOperation,
                Point destinationPoint, Pilot pilot, String operationAtBerth,
                Long callId, Long externalId) {
        this.vessel = vessel;
        this.pointOfOperation = pointOfOperation;
        this.operation = operation;
        this.timeAndDateOfOperation = timeAndDateOfOperation;
        this.destinationPoint = destinationPoint;
        this.pilot = pilot;
        this.operationAtBerth = operationAtBerth;
        this.callId = callId;
        this.externalId = externalId;
    }

    public Move(Vessel vessel, Point pointOfOperation,
                Operation operation, LocalDateTime timeAndDateOfOperation,
                Point destinationPoint, Pilot pilot, Agent agent,
                String operationAtBerth, Long callId, Long externalId) {
        this.vessel = vessel;
        this.pointOfOperation = pointOfOperation;
        this.operation = operation;
        this.timeAndDateOfOperation = timeAndDateOfOperation;
        this.destinationPoint = destinationPoint;
        this.pilot = pilot;
        this.agent = agent;
        this.operationAtBerth = operationAtBerth;
        this.callId = callId;
        this.externalId = externalId;
    }
}
