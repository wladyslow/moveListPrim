package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.VesselDto;
import ru.wladyslow.moveList.entities.Vessel;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VesselMapper {

    VesselDto toDto(Vessel vesselEntity);

    List<VesselDto> toDtos(List<Vessel> vesselEntityList);

    Vessel toEntity(VesselDto vesselDto);

    List<Vessel> toEntities(List<VesselDto> vesselDtoList);

    default Optional<VesselDto> toOptional(Optional<Vessel> vesselEntity) {
        return vesselEntity.map(this::toDto);
    }
}
