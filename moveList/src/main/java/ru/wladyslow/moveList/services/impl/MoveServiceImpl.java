package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.*;
import ru.wladyslow.moveList.mapper.*;
import ru.wladyslow.moveList.repositories.MoveRepository;
import ru.wladyslow.moveList.services.MoveService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;
    private final MoveMapper moveMapper;
    private final VesselMapper vesselMapper;
    private final PointMapper pointMapper;
    private final OperationMapper operationMapper;
    private final PilotMapper pilotMapper;
    private final AgentMapper agentMapper;

    @Override
    public Long createMove(VesselDto vessel, PointDto pointOfOperation,
                           OperationDto operation, LocalDateTime timeAndDateOfOperation,
                           PointDto destinationPoint, PilotDto pilot, String operationAtBerth,
                           Long callId, Long externalId) {
        val moveDto = new MoveDto(vessel, pointOfOperation,
                operation, timeAndDateOfOperation,
                destinationPoint, pilot, operationAtBerth,
                callId, externalId);
        val move = moveMapper.toEntity(moveDto);
        moveRepository.save(move);
        return move.getId();
    }

    @Override
    @Transactional
    public List<MoveDto> findAll() {
        return moveMapper.toDtos(moveRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MoveDto> findById(Long id) {
        return moveMapper.toOptional(moveRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MoveDto> findByCallId(Long callId) {
        return moveMapper.toOptional(moveRepository.findByCallId(callId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MoveDto> findByExternalId(Long externalId) {
        return moveMapper.toOptional(moveRepository.findByExternalId(externalId));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        moveRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, VesselDto vessel, PointDto pointOfOperation,
                       OperationDto operation, LocalDateTime timeAndDateOfOperation,
                       PointDto destinationPoint, PilotDto pilot, AgentDto agent, String operationAtBerth,
                       Long callId, Long externalId) {
        moveRepository.findById(id).ifPresent(move -> {
            move.setVessel(vesselMapper.toEntity(vessel));
            move.setPointOfOperation(pointMapper.toEntity(pointOfOperation));
            move.setOperation(operationMapper.toEntity(operation));
            move.setTimeAndDateOfOperation(timeAndDateOfOperation);
            move.setDestinationPoint(pointMapper.toEntity(destinationPoint));
            move.setPilot(pilotMapper.toEntity(pilot));
            move.setAgent(agentMapper.toEntity(agent));
            move.setOperationAtBerth(operationAtBerth);
            move.setCallId(callId);
            move.setExternalId(externalId);
        });
    }

    @Override
    @Transactional
    public MoveDto createOrUpdate(VesselDto vessel,
                                  PointDto pointOfOperation,
                                  OperationDto operation,
                                  LocalDateTime timeAndDateOfOperation,
                                  PointDto destinationPoint, PilotDto pilot, AgentDto agent,
                                  String operationAtBerth, Long callId,
                                  Long externalId) {
        val moveDtoOptional = moveMapper.toOptional(moveRepository.findByExternalId(externalId));
        if (moveDtoOptional.isEmpty()) {
            val moveDto = new MoveDto(vessel,
                    pointOfOperation,
                    operation,
                    timeAndDateOfOperation,
                    destinationPoint, pilot, agent,
                    operationAtBerth, callId,
                    externalId);
            val move = moveMapper.toEntity(moveDto);
            moveRepository.save(move);
            return moveMapper.toDto(move);
        } else {
            moveRepository.findByExternalId(externalId).ifPresent(move -> {
                move.setVessel(vesselMapper.toEntity(vessel));
                move.setPointOfOperation(pointMapper.toEntity(pointOfOperation));
                move.setOperation(operationMapper.toEntity(operation));
                move.setTimeAndDateOfOperation(timeAndDateOfOperation);
                move.setDestinationPoint(pointMapper.toEntity(destinationPoint));
                move.setPilot(pilotMapper.toEntity(pilot));
                move.setAgent(agentMapper.toEntity(agent));
                move.setOperationAtBerth(operationAtBerth);
                move.setCallId(callId);
            });
            return moveMapper.toDto(moveRepository.findByExternalId(externalId).get());
        }

    }

    @Override
    @Transactional
    public List<MoveDto> findAllByIsSent(boolean isSent) {
        return moveMapper.toDtos(moveRepository.findAllByIsSent(isSent));
    }

    @Override
    @Transactional
    public void messageIsSent(Long id) {
        moveRepository.findById(id).ifPresent(move -> {
            move.setSent(true);
        });
    }

    @Override
    public List<MoveDto> findLastFiveMoves() {
        return moveMapper.toDtos(moveRepository.findTop5ByOrderByExternalIdDesc());
    }
}
