package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.FlagDto;
import ru.wladyslow.moveList.entities.Flag;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FlagMapper {

    FlagDto toDto(Flag flagEntity);

    List<FlagDto> toDtos(List<Flag> flagEntityList);

    Flag toEntity(FlagDto flagDto);

    List<Flag> toEntities(List<FlagDto> flagDtoList);

    default Optional<FlagDto> toOptional(Optional<Flag> flagEntity) {
        return flagEntity.map(this::toDto);
    }
}
