package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class PortScheduleDto {

    private Long id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate scheduleDate;

    private List<ScheduledOperationDto> scheduledOperations;

    private boolean isSent;

    private boolean isActual;

    public PortScheduleDto(LocalDate scheduleDate,
                           List<ScheduledOperationDto> scheduledOperations) {
        this.scheduleDate = scheduleDate;
        this.scheduledOperations = scheduledOperations;
    }

    public boolean equals(PortScheduleDto portSchedule) {
        return this.scheduledOperations.equals(portSchedule.scheduledOperations);
    }

    public String getScheduleMessage(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        DateTimeFormatter formatterOfDates = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = this.getScheduleDate().format(formatterOfDates);
        String mes = "Приморск: Плановый день ";
        mes = mes.concat(formattedDate) + ":";
        for (ScheduledOperationDto scheduledOperationDto : this.getScheduledOperations()) {
            String message = "\n" + scheduledOperationDto.getScheduledOperationName() + " / " +
                    scheduledOperationDto.getTimeAndDateOfScheduledOperation().format(formatter) + " / " +
                    scheduledOperationDto.getVessel().getVesselFinderLink() + " / " +
                    scheduledOperationDto.getAgent().getName() + " / " +
                    scheduledOperationDto.getRoute() + " / " +
                    scheduledOperationDto.getPilotCompanyName() + " / " +
                    scheduledOperationDto.getExternalId();
            mes = mes.concat(message);
        }
        return mes;
    }
}