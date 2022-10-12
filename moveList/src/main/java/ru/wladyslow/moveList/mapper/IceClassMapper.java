package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.IceClassDto;
import ru.wladyslow.moveList.entities.IceClass;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IceClassMapper {

    IceClassDto toDto(IceClass iceClassEntity);

    List<IceClassDto> toDtos(List<IceClass> iceClassEntityList);

    IceClass toEntity(IceClassDto iceClassDto);

    List<IceClass> toEntities(List<IceClassDto> iceClassDtoList);

    default Optional<IceClassDto> toOptional(Optional<IceClass> iceClassEntity) {
        return iceClassEntity.map(this::toDto);
    }
}
