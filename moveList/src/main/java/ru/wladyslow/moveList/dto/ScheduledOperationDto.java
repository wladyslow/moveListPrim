package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ScheduledOperationDto {

    private Long id;

    private String scheduledOperationName;

    @DateTimeFormat(pattern = "HH:mm dd.MM.yy")
    private LocalDateTime timeAndDateOfScheduledOperation;

    private VesselDto vessel;

    private AgentDto agent;

    @Column
    private String route;

    @Column
    private String pilotCompanyName;

    @Column
    private Long externalId;

    public ScheduledOperationDto(String scheduledOperationName,
                                 LocalDateTime timeAndDateOfScheduledOperation,
                                 VesselDto vessel, AgentDto agent, String route,
                                 String pilotCompanyName, Long externalId) {
        this.scheduledOperationName = scheduledOperationName;
        this.timeAndDateOfScheduledOperation = timeAndDateOfScheduledOperation;
        this.vessel = vessel;
        this.agent = agent;
        this.route = route;
        this.pilotCompanyName = pilotCompanyName;
        this.externalId = externalId;
    }

    public boolean equals(ScheduledOperationDto scheduledOperationDto) {
        return this.scheduledOperationName.equals(scheduledOperationDto.scheduledOperationName) &&
                this.timeAndDateOfScheduledOperation.equals(scheduledOperationDto.timeAndDateOfScheduledOperation) &&
                this.vessel.equals(scheduledOperationDto.vessel) &&
                this.agent.equals(scheduledOperationDto.agent) &&
                this.route.equals(scheduledOperationDto.route) &&
                this.pilotCompanyName.equals(scheduledOperationDto.pilotCompanyName) &&
                this.externalId.equals(scheduledOperationDto.externalId);
    }


}
