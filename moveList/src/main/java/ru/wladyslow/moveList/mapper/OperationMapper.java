package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.OperationDto;
import ru.wladyslow.moveList.entities.Operation;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OperationMapper {

    OperationDto toDto(Operation operationEntity);

    List<OperationDto> toDtos(List<Operation> operationEntityList);

    Operation toEntity(OperationDto operationDto);

    List<Operation> toEntities(List<OperationDto> operationDtoList);

    default Optional<OperationDto> toOptional(Optional<Operation> operationEntity) {
        return operationEntity.map(this::toDto);
    }
}
