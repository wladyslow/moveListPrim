package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.VesselDto;

public interface FindNewMovesService {

    void findNewMovesAndPostThem();

    VesselDto getVesselDto(String urlEnd);
}
