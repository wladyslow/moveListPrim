package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.PointDto;

import java.util.List;
import java.util.Optional;

public interface PointService {

    Long createPoint(String name);

    List<PointDto> findAll();

    Optional<PointDto> findById(Long id);

    Optional<PointDto> findByName(String name);

    void deleteById(Long id);

    void update(Long id, String name);

    PointDto createOrUpdate(String name);
}
