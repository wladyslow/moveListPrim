package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.FlagDto;

import java.util.List;
import java.util.Optional;

public interface FlagService {

    Long createFlag(String rusName);

    List<FlagDto> findAll();

    Optional<FlagDto> findById(Long id);

    Optional<FlagDto> findByRusName(String rusName);

    Optional<FlagDto> findByEngName(String engName);

    FlagDto createOrUpdate(String rusName);

    void deleteById(Long id);

    void update(Long id, String rusName, String engName);
}
