package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.AgentDto;
import ru.wladyslow.moveList.mapper.AgentMapper;
import ru.wladyslow.moveList.repositories.AgentRepository;
import ru.wladyslow.moveList.services.AgentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentMapper agentMapper;
    private final AgentRepository agentRepository;

    @Override
    @Transactional
    public Long createAgent(String name) {
        val agentDto = new AgentDto(name);
        val agent = agentMapper.toEntity(agentDto);
        agentRepository.save(agent);
        return agent.getId();
    }

    @Override
    @Transactional
    public List<AgentDto> findAll() {
        return agentMapper.toDtos(agentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgentDto> findById(Long id) {
        return agentMapper.toOptional(agentRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgentDto> findByName(String name) {
        return agentMapper.toOptional(agentRepository.findByName(name));
    }

    @Override
    @Transactional
    public AgentDto updateOrCreate(String name) {
        val agentDtoOptional = agentMapper.toOptional(agentRepository.findByName(name));
        if (agentDtoOptional.isEmpty()) {
            val agentDto = new AgentDto(name);
            val agent = agentMapper.toEntity(agentDto);
            agentRepository.save(agent);
            return agentMapper.toDto(agentRepository.findById(agent.getId()).get());
        } else {
            return agentDtoOptional.get();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        agentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        agentRepository.findById(id).ifPresent(agent -> {
            agent.setName(name);
        });
    }
}
