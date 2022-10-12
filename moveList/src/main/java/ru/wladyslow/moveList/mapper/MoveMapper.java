package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.entities.Move;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MoveMapper {

    MoveDto toDto(Move moveEntity);

    List<MoveDto> toDtos(List<Move> moveEntityList);

    Move toEntity(MoveDto moveDto);

    List<Move> toEntities(List<MoveDto> moveDtoList);

    default Optional<MoveDto> toOptional(Optional<Move> moveEntity) {
        return moveEntity.map(this::toDto);
    }
}
