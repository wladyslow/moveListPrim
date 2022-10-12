package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.OperationDto;
import ru.wladyslow.moveList.dto.PilotDto;
import ru.wladyslow.moveList.mapper.PilotMapper;
import ru.wladyslow.moveList.repositories.PilotRepository;
import ru.wladyslow.moveList.services.PilotService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PilotServiceImpl implements PilotService {

    private final PilotMapper pilotMapper;
    private final PilotRepository pilotRepository;

    @Override
    @Transactional
    public Long createPilot(String name) {
        val pilotDto = new PilotDto(name);
        val pilot = pilotMapper.toEntity(pilotDto);
        pilotRepository.save(pilot);
        return pilot.getId();
    }

    @Override
    @Transactional
    public List<PilotDto> findAll() {
        return pilotMapper.toDtos(pilotRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PilotDto> findById(Long id) {
        return pilotMapper.toOptional(pilotRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PilotDto> findByName(String name) {
        return pilotMapper.toOptional(pilotRepository.findByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PilotDto> findByEngName(String engName) {
        return pilotMapper.toOptional(pilotRepository.findByEngName(engName));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pilotRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name, String engName) {
        pilotRepository.findById(id).ifPresent(pilot -> {
            pilot.setName(name);
            pilot.setEngName(engName);
        });
    }

    @Override
    @Transactional
    public PilotDto createOrUpdate(String name) {
        val pilotDtoOptional = pilotRepository.findByName(name);
        if (pilotDtoOptional.isEmpty()) {
            val pilotDto = new PilotDto(name);
            val pilot = pilotMapper.toEntity(pilotDto);
            pilotRepository.save(pilot);
            return pilotMapper.toDto(pilotRepository.findById(pilot.getId()).get());
        }
        return pilotMapper.toDto(pilotDtoOptional.get());
    }
}
