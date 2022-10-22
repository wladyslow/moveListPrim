package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class MoveDto implements Comparable<MoveDto>{

    private Long id;

    private VesselDto vessel;

    private PointDto pointOfOperation;

    private OperationDto operation;

    @DateTimeFormat(pattern = "HH:mm dd.MM.yy")
    private LocalDateTime timeAndDateOfOperation;

    private PointDto destinationPoint;

    private PilotDto pilot;

    private String operationAtBerth;

    private Long callId;

    private Long externalId;

    private boolean isSent;

    public MoveDto(VesselDto vessel, PointDto pointOfOperation, OperationDto operation,
                   LocalDateTime timeAndDateOfOperation, PointDto destinationPoint,
                   PilotDto pilot, String operationAtBerth, Long callId, Long externalId, boolean isSent) {
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

    public MoveDto(VesselDto vessel, PointDto pointOfOperation,
                   OperationDto operation, LocalDateTime timeAndDateOfOperation,
                   PointDto destinationPoint, PilotDto pilot, String operationAtBerth,
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

    @Override
    public int compareTo(MoveDto o) {
        return this.getTimeAndDateOfOperation().compareTo(o.getTimeAndDateOfOperation());
    }

    public String getMoveDtoMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDateTime = this.getTimeAndDateOfOperation().format(formatter);
        return this.getVessel().getName() + " / " +
                this.getVessel().getVesselFinderLink() +
                " (" +
                this.getVessel().getImo() + ") " +
                this.getVessel().getFlag().getRusName() + " " +
                this.getVessel().getType() + " " +
                this.getVessel().getAgent().getName() + " " +
                this.getOperation().getName() + " " +
                this.getPointOfOperation().getName() + " " +
                formattedDateTime + " " +
                this.getPilot().getName();
    }
}