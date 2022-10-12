package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.AgentDto;
import ru.wladyslow.moveList.entities.Agent;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AgentMapper {

    AgentDto toDto(Agent agentEntity);

    List<AgentDto> toDtos(List<Agent> agentEntityList);

    Agent toEntity(AgentDto agentDto);

    List<Agent> toEntities(List<AgentDto> agentDtoList);

    default Optional<AgentDto> toOptional(Optional<Agent> agentEntity) {
        return agentEntity.map(this::toDto);
    }
}


