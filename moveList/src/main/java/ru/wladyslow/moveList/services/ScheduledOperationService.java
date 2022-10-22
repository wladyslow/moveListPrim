package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.AgentDto;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;
import ru.wladyslow.moveList.dto.VesselDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduledOperationService {
    Long createScheduledOperation(String scheduledOperationName,
                                  LocalDateTime timeAndDateOfScheduledOperation,
                                  VesselDto vessel, AgentDto agent, String route,
                                  String pilotCompanyName, Long externalId);

    List<ScheduledOperationDto> findAll();

    Optional<ScheduledOperationDto> findById(Long id);

    Optional<ScheduledOperationDto> findByExternalId(Long externalId);

    void deleteById(Long id);

    ScheduledOperationDto createOrUpdate(String scheduledOperationName,
                                         LocalDateTime timeAndDateOfScheduledOperation,
                                         VesselDto vessel, AgentDto agent, String route,
                                         String pilotCompanyName, Long externalId);
}
