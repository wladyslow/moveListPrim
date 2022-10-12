package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.OperationDto;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    Long createOperation(String name);

    List<OperationDto> findAll();

    Optional<OperationDto> findById(Long id);

    Optional<OperationDto> findByName(String name);

    void deleteById(Long id);

    void update(Long id, String name);

    OperationDto createOrUpdate(String name);
}
