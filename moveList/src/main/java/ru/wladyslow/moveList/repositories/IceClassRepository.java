package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.IceClass;

import java.util.Optional;

public interface IceClassRepository extends JpaRepository<IceClass, Long> {
    Optional<IceClass> findByName(String name);
}
