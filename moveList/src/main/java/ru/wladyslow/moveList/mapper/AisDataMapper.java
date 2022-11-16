package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.AisDataDto;
import ru.wladyslow.moveList.entities.AisData;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AisDataMapper {

    AisDataDto toDto(AisData aisDataEntity);

    List<AisDataDto> toDtos(List<AisData> aisDataEntityList);

    AisData toEntity(AisDataDto aisDataDto);

    List<AisData> toEntities(List<AisDataDto> aisDataDtoList);

    default Optional<AisDataDto> toOptional(Optional<AisData> aisDataEntity) {
        return aisDataEntity.map(this::toDto);
    }
}
