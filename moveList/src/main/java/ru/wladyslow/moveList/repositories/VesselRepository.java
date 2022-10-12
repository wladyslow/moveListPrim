package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.Vessel;

import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Long> {

    Optional<Vessel> findByExternalId(Long externalId);

    Optional<Vessel> findByNameImo(String nameImo);

    Optional<Vessel> findByName(String name);

    Optional<Vessel> findByEngName(String engName);
}