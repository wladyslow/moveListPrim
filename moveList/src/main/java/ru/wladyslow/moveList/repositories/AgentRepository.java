package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.Agent;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByName(String name);
}


