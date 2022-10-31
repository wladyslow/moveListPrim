package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.ScheduledOperation;

import java.util.Optional;

public interface ScheduledOperationRepository extends JpaRepository<ScheduledOperation, Long> {
    Optional<ScheduledOperation> findByExternalId(Long externalId);
}
