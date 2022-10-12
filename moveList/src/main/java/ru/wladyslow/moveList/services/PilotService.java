package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.PilotDto;

import java.util.List;
import java.util.Optional;

public interface PilotService {

    Long createPilot(String name);

    List<PilotDto> findAll();

    Optional<PilotDto> findById(Long id);

    Optional<PilotDto> findByName(String name);

    Optional<PilotDto> findByEngName(String engName);

    void deleteById(Long id);

    void update(Long id, String name, String engName);

    PilotDto createOrUpdate(String name);
}