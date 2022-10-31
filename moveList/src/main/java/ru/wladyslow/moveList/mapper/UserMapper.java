package ru.wladyslow.moveList.mapper;

import org.mapstruct.Mapper;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.entities.User;


import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    UserDto toDto(User userEntity);

    List<UserDto> toDtos(List<User> userEntityList);

    User toEntity(UserDto userDto);

    List<User> toEntities(List<UserDto> userDtoList);

    default Optional<UserDto> toOptional(Optional<User> userEntity) {
        return userEntity.map(this::toDto);
    }

}
