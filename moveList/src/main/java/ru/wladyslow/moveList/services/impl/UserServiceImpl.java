package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.mapper.UserMapper;
import ru.wladyslow.moveList.repositories.UserRepository;
import ru.wladyslow.moveList.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto createUser(Long chatId) {
        val userDto = new UserDto(chatId);
        val user = userMapper.toEntity(userDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public List<UserDto> findAll() {
        return userMapper.toDtos(userRepository.findAll());
    }

    @Override
    @Transactional
    public Optional<UserDto> findById(Long id) {
        return userMapper.toOptional(userRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<UserDto> findByName(String name) {
        return userMapper.toOptional(userRepository.findByName(name));
    }

    @Override
    @Transactional
    public Optional<UserDto> findByChatId(Long chatId) {
        return userMapper.toOptional(userRepository.findByChatId(chatId));
    }

    @Override
    @Transactional
    public UserDto createOrUpdate(Long chatId) {
        val userDtoOpt = findByChatId(chatId);
        if (userDtoOpt.isEmpty()) {
            return createUser(chatId);
        }
        return userDtoOpt.get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name, Long chatId) {
        userRepository.findById(id).ifPresent(user -> {
            user.setName(name);
            user.setChatId(chatId);
        });
    }

    @Override
    public void saveChanges(UserDto user) {
        userRepository.save(userMapper.toEntity(user));
    }

    @Override
    public UserDto updateOrCreate(Long chatId, String firstName, String lastNAme) {
        val userDtoOpt = findByChatId(chatId);
        if (userDtoOpt.isEmpty()) {
            val userDto = new UserDto(chatId);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastNAme);
            val user = userMapper.toEntity(userDto);
            userRepository.save(user);
            return userMapper.toDto(user);
        } else {
            val userDto = userDtoOpt.get();
            userDto.setFirstName(firstName);
            userDto.setLastName(lastNAme);
            val user = userMapper.toEntity(userDto);
            userRepository.save(user);
            return userMapper.toDto(user);
        }
    }
}
