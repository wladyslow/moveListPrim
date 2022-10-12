package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.AgentDto;

import java.util.List;
import java.util.Optional;

public interface AgentService {

    Long createAgent(String name);

    List<AgentDto> findAll();

    Optional<AgentDto> findById(Long id);

    Optional<AgentDto> findByName(String name);

    AgentDto updateOrCreate(String name);

    void deleteById(Long id);

    void update(Long id, String name);
}



