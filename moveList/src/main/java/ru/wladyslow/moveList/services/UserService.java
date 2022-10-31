package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createUser(Long chatId);

    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    Optional<UserDto> findByName(String name);

    Optional<UserDto> findByChatId(Long chatId);

    UserDto createOrUpdate(Long chatId);

    void deleteById(Long id);

    void update(Long id, String name, Long chatId);

    void saveChanges(UserDto user);
}
