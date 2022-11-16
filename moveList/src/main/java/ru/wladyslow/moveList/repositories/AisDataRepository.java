package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.AisData;
import ru.wladyslow.moveList.entities.Vessel;

import java.util.List;
import java.util.Optional;

public interface AisDataRepository extends JpaRepository<AisData, Long> {

    Optional<AisData> findByVessel(Vessel vessel);

    List<AisData> findByEtaDestination(String etaDestination);

    Optional<AisData> findByVesselId(Long vesselId);
}
