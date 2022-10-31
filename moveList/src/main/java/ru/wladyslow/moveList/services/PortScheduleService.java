package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PortScheduleService {

    Long createPortSchedule(LocalDate scheduleDate,
                            List<ScheduledOperationDto> scheduledOperations);

    List<PortScheduleDto> findAll();

    Optional<PortScheduleDto> findById(Long id);

    Optional<PortScheduleDto> findByScheduleDate(LocalDate scheduleDate);

    void deleteById(Long id);

    void update(LocalDate scheduleDate,
                List<ScheduledOperationDto> scheduledOperations);

    PortScheduleDto createOrUpdate(LocalDate scheduleDate,
                                   List<ScheduledOperationDto> scheduledOperations);
}
