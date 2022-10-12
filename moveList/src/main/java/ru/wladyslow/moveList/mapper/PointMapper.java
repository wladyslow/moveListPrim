package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.PointDto;
import ru.wladyslow.moveList.entities.Point;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PointMapper {

    PointDto toDto(Point pointEntity);

    List<PointDto> toDtos(List<Point> pointEntityList);

    Point toEntity(PointDto pointDto);

    List<Point> toEntities(List<PointDto> pointDtoList);

    default Optional<PointDto> toOptional(Optional<Point> pointEntity) {
        return pointEntity.map(this::toDto);
    }
}
