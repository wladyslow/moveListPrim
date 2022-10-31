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
@Table(name = "SCHEDULED_OPERATION")
public class ScheduledOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PORT_SCHEDULE_ID", nullable = false)
    private PortSchedule portSchedule;

    @Column
    private String scheduledOperationName;

    @Column
    @DateTimeFormat(pattern = "HH:mm dd.MM.yy")
    private LocalDateTime timeAndDateOfScheduledOperation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "VESSEL_ID")
    private Vessel vessel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @Column
    private String route;

    @Column
    private String pilotCompanyName;

    @Column
    private Long externalId;

    public ScheduledOperation(PortSchedule portSchedule, String scheduledOperationName,
                              LocalDateTime timeAndDateOfScheduledOperation,
                              Vessel vessel, Agent agent, String route,
                              String pilotCompanyName, Long externalId) {
        this.portSchedule = portSchedule;
        this.scheduledOperationName = scheduledOperationName;
        this.timeAndDateOfScheduledOperation = timeAndDateOfScheduledOperation;
        this.vessel = vessel;
        this.agent = agent;
        this.route = route;
        this.pilotCompanyName = pilotCompanyName;
        this.externalId = externalId;
    }
}
