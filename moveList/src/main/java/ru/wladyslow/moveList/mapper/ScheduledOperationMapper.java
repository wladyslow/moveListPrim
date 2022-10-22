package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;
import ru.wladyslow.moveList.entities.ScheduledOperation;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ScheduledOperationMapper {

    ScheduledOperationDto toDto(ScheduledOperation scheduledOperationEntity);

    List<ScheduledOperationDto> toDtos(List<ScheduledOperation> scheduledOperationEntityList);

    ScheduledOperation toEntity(ScheduledOperationDto scheduledOperationDto);

    List<ScheduledOperation> toEntities(List<ScheduledOperationDto> scheduledOperationDtoList);

    default Optional<ScheduledOperationDto> toOptional(Optional<ScheduledOperation> scheduledOperationEntity) {
        return scheduledOperationEntity.map(this::toDto);
    }
}
