package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.IceClassDto;
import ru.wladyslow.moveList.dto.OperationDto;
import ru.wladyslow.moveList.mapper.OperationMapper;
import ru.wladyslow.moveList.repositories.OperationRepository;
import ru.wladyslow.moveList.services.OperationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationMapper operationMapper;
    private final OperationRepository operationRepository;

    @Override
    @Transactional
    public Long createOperation(String name) {
        val operationDto = new OperationDto(name);
        val operation = operationMapper.toEntity(operationDto);
        operationRepository.save(operation);
        return operation.getId();
    }

    @Override
    @Transactional
    public List<OperationDto> findAll() {
        return operationMapper.toDtos(operationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationDto> findById(Long id) {
        return operationMapper.toOptional(operationRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationDto> findByName(String name) {
        return operationMapper.toOptional(operationRepository.findByName(name));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        operationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        operationRepository.findById(id).ifPresent(operation -> {
            operation.setName(name);
        });
    }

    @Override
    @Transactional
    public OperationDto createOrUpdate(String name) {
        val operationDtoOptional = operationRepository.findByName(name);
        if (operationDtoOptional.isEmpty()) {
            val operationDto = new OperationDto(name);
            val operation = operationMapper.toEntity(operationDto);
            operationRepository.save(operation);
            return operationMapper.toDto(operationRepository.findById(operation.getId()).get());
        }
        return operationMapper.toDto(operationDtoOptional.get());
    }
}
