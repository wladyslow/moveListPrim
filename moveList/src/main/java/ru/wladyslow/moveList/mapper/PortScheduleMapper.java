package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.entities.PortSchedule;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PortScheduleMapper {
    PortScheduleDto toDto(PortSchedule portScheduleEntity);

    List<PortScheduleDto> toDtos(List<PortSchedule> portScheduleEntityList);

    PortSchedule toEntity(PortScheduleDto portScheduleDto);

    List<PortSchedule> toEntities(List<PortScheduleDto> portScheduleDtoList);

    default Optional<PortScheduleDto> toOptional(Optional<PortSchedule> portScheduleEntity) {
        return portScheduleEntity.map(this::toDto);
    }
}
