package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.Pilot;

import java.util.Optional;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    Optional<Pilot> findByName(String name);

    Optional<Pilot> findByEngName(String engName);
}
