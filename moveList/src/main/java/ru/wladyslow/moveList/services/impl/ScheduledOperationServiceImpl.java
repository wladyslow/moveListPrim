package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.AgentDto;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;
import ru.wladyslow.moveList.dto.VesselDto;
import ru.wladyslow.moveList.mapper.AgentMapper;
import ru.wladyslow.moveList.mapper.ScheduledOperationMapper;
import ru.wladyslow.moveList.mapper.VesselMapper;
import ru.wladyslow.moveList.repositories.ScheduledOperationRepository;
import ru.wladyslow.moveList.services.ScheduledOperationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduledOperationServiceImpl implements ScheduledOperationService {

    private final ScheduledOperationRepository scheduledOperationRepository;
    private final ScheduledOperationMapper scheduledOperationMapper;
    private final VesselMapper vesselMapper;
    private final AgentMapper agentMapper;

    @Override
    @Transactional
    public Long createScheduledOperation(String scheduledOperationName,
                                         LocalDateTime timeAndDateOfScheduledOperation,
                                         VesselDto vessel, AgentDto agent, String route,
                                         String pilotCompanyName, Long externalId) {
        val scheduledOperationDto = new ScheduledOperationDto(scheduledOperationName,
                timeAndDateOfScheduledOperation,
                vessel, agent, route,
                pilotCompanyName, externalId);
        val scheduledOperation = scheduledOperationMapper.toEntity(scheduledOperationDto);
        scheduledOperationRepository.save(scheduledOperation);
        return scheduledOperation.getId();
    }

    @Override
    @Transactional
    public List<ScheduledOperationDto> findAll() {
        return scheduledOperationMapper.toDtos(scheduledOperationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduledOperationDto> findById(Long id) {
        return scheduledOperationMapper.toOptional(scheduledOperationRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<ScheduledOperationDto> findByExternalId(Long externalId) {
        return scheduledOperationMapper.toOptional(scheduledOperationRepository.findByExternalId(externalId));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        scheduledOperationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ScheduledOperationDto createOrUpdate(String scheduledOperationName,
                                                LocalDateTime timeAndDateOfScheduledOperation,
                                                VesselDto vessel, AgentDto agent, String route,
                                                String pilotCompanyName, Long externalId) {
        val scheduledOperationDtoOpt = findByExternalId(externalId);
        if (scheduledOperationDtoOpt.isEmpty()) {
            val scheduledOperationDto = new ScheduledOperationDto(scheduledOperationName,
                    timeAndDateOfScheduledOperation,
                    vessel, agent, route,
                    pilotCompanyName, externalId);
            val scheduledOperation = scheduledOperationMapper.toEntity(scheduledOperationDto);
            scheduledOperationRepository.save(scheduledOperation);
            return findByExternalId(externalId).get();
        } else {
            val scheduledOperationDtoDb = scheduledOperationDtoOpt.get();
            val scheduledOperationDtoExt
                    = new ScheduledOperationDto(scheduledOperationName,
                    timeAndDateOfScheduledOperation,
                    vessel, agent, route,
                    pilotCompanyName, externalId);
            if (scheduledOperationDtoDb.equals(scheduledOperationDtoExt)) {
                return scheduledOperationDtoDb;
            } else {
                scheduledOperationRepository.findByExternalId(externalId).ifPresent(scheduledOperation -> {
                    scheduledOperation.setScheduledOperationName(scheduledOperationName);
                    scheduledOperation.setTimeAndDateOfScheduledOperation(timeAndDateOfScheduledOperation);
                    scheduledOperation.setVessel(vesselMapper.toEntity(vessel));
                    scheduledOperation.setAgent(agentMapper.toEntity(agent));
                    scheduledOperation.setRoute(route);
                    scheduledOperation.setPilotCompanyName(pilotCompanyName);
                });
                return findByExternalId(externalId).get();
            }
        }
    }
}
