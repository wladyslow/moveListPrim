package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.AisDataDto;
import ru.wladyslow.moveList.dto.VesselDto;

import java.util.List;
import java.util.Optional;

public interface AisDataService {

    List<AisDataDto> findAll();

    Optional<AisDataDto> findById(Long id);

    Optional<AisDataDto> findByVessel(VesselDto vessel);

    Optional<AisDataDto> findByVesselId(Long vesselId);

    AisDataDto updateOrCreate(Long vesselId, String urlLink);

    void deleteById(Long id);
}
