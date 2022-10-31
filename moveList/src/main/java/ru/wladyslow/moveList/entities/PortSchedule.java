package ru.wladyslow.moveList.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PORT_SCHEDULE")
public class PortSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate scheduleDate;

    @OneToMany(mappedBy = "portSchedule")
    private List<ScheduledOperation> scheduledOperations;

    @Column
    private boolean isSent;

    @Column
    private boolean isActual;

    public PortSchedule(LocalDate scheduleDate,
                        List<ScheduledOperation> scheduledOperations) {
        this.scheduleDate = scheduleDate;
        this.scheduledOperations = scheduledOperations;
    }

    public boolean equals(PortSchedule portSchedule) {
        return this.scheduledOperations == portSchedule.scheduledOperations;
    }
}
