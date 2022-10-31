package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;
import ru.wladyslow.moveList.entities.ScheduledOperation;
import ru.wladyslow.moveList.mapper.PortScheduleMapper;
import ru.wladyslow.moveList.mapper.ScheduledOperationMapper;
import ru.wladyslow.moveList.repositories.PortScheduleRepository;
import ru.wladyslow.moveList.repositories.ScheduledOperationRepository;
import ru.wladyslow.moveList.services.PortScheduleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortScheduleServiceImpl implements PortScheduleService {

    private final PortScheduleMapper portScheduleMapper;
    private final PortScheduleRepository portScheduleRepository;
    private final ScheduledOperationMapper scheduledOperationMapper;
    private final ScheduledOperationRepository scheduledOperationRepository;

    @Override
    @Transactional
    public Long createPortSchedule(LocalDate scheduleDate,
                                   List<ScheduledOperationDto> scheduledOperations) {
        val portScheduleDto = new PortScheduleDto(scheduleDate, scheduledOperations);
        val portSchedule = portScheduleMapper.toEntity(portScheduleDto);
        portScheduleRepository.save(portSchedule);
        return portSchedule.getId();
    }

    @Override
    @Transactional
    public List<PortScheduleDto> findAll() {
        return portScheduleMapper.toDtos(portScheduleRepository.findAll());
    }

    @Override
    @Transactional
    public Optional<PortScheduleDto> findById(Long id) {
        return portScheduleMapper.toOptional(portScheduleRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<PortScheduleDto> findByScheduleDate(LocalDate scheduleDate) {
        return portScheduleMapper.toOptional(portScheduleRepository.findByScheduleDate(scheduleDate));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        portScheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(LocalDate scheduleDate,
                       List<ScheduledOperationDto> scheduledOperations) {
        portScheduleRepository.findByScheduleDate(scheduleDate).ifPresent(portSchedule -> {
            portSchedule.setScheduledOperations(scheduledOperationMapper.toEntities(scheduledOperations));
        });
    }

    @Override
    @Transactional
    public PortScheduleDto createOrUpdate(LocalDate scheduleDate,
                                          List<ScheduledOperationDto> scheduledOperations) {
        val portScheduleDtoOpt = findByScheduleDate(scheduleDate);
        if (portScheduleDtoOpt.isEmpty()) {
            val portScheduleDto = new PortScheduleDto(scheduleDate, scheduledOperations);
            val portSchedule = portScheduleMapper.toEntity(portScheduleDto);
            portScheduleRepository.save(portSchedule);
            for (ScheduledOperation scheduledOperation : scheduledOperationMapper.toEntities(scheduledOperations)) {
                scheduledOperation.setPortSchedule(portSchedule);
                scheduledOperationRepository.save(scheduledOperation);
            }
            return findByScheduleDate(scheduleDate).get();
        } else {
            val portScheduleDtoDb = portScheduleDtoOpt.get();
            val portScheduleDtoExt = new PortScheduleDto(scheduleDate, scheduledOperations);
            if (portScheduleDtoDb.equals(portScheduleDtoExt)) {
                return portScheduleDtoDb;
            } else {
                portScheduleRepository.findByScheduleDate(scheduleDate).ifPresent(portSchedule -> {
                    portSchedule.setScheduledOperations(scheduledOperationMapper.toEntities(scheduledOperations));
                });
                for (ScheduledOperation scheduledOperation : scheduledOperationMapper.toEntities(scheduledOperations)) {
                    scheduledOperation.setPortSchedule(portScheduleRepository.findByScheduleDate(scheduleDate).get());
                    scheduledOperationRepository.save(scheduledOperation);

                }
                return findByScheduleDate(scheduleDate).get();
            }
        }
    }
}
