package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.IceClassDto;

import java.util.List;
import java.util.Optional;

public interface IceClassService {

    Long createIceClass(String name);

    List<IceClassDto> findAll();

    Optional<IceClassDto> findById(Long id);

    Optional<IceClassDto> findByName(String name);

    void deleteById(Long id);

    void update(Long id, String name);

    IceClassDto createOrUpdate(String name);
}
