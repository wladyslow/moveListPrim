package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MoveService {

    Long createMove(VesselDto vessel, PointDto pointOfOperation, OperationDto operation,
                    LocalDateTime timeAndDateOfOperation, PointDto destinationPoint,
                    PilotDto pilot, String operationAtBerth, Long callId, Long externalId);

    List<MoveDto> findAll();

    Optional<MoveDto> findById(Long id);

    Optional<MoveDto> findByCallId(Long callId);

    Optional<MoveDto> findByExternalId(Long externalId);

    void deleteById(Long id);

    void update(Long id, VesselDto vessel, PointDto pointOfOperation, OperationDto operation,
                LocalDateTime timeAndDateOfOperation, PointDto destinationPoint,
                PilotDto pilot, AgentDto agent, String operationAtBerth, Long callId, Long externalId);

    MoveDto createOrUpdate(VesselDto vessel, PointDto pointOfOperation, OperationDto operation,
                           LocalDateTime timeAndDateOfOperation, PointDto destinationPoint,
                           PilotDto pilot, AgentDto agent, String operationAtBerth, Long callId, Long externalId);

    List <MoveDto> findAllByIsSent(boolean isSent);

    void messageIsSent(Long id);

    List<MoveDto> findLastFiveMoves();
}
