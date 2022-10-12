package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.PilotDto;
import ru.wladyslow.moveList.entities.Pilot;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PilotMapper {

    PilotDto toDto(Pilot pilotEntity);

    List<PilotDto> toDtos(List<Pilot> pilotEntityList);

    Pilot toEntity(PilotDto pilotDto);

    List<Pilot> toEntities(List<PilotDto> pilotDtoList);

    default Optional<PilotDto> toOptional(Optional<Pilot> pilotEntity) {
        return pilotEntity.map(this::toDto);
    }
}
