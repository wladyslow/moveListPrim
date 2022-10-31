package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByChatId(Long chatId);
}
