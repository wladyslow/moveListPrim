package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.Move;

import java.util.List;
import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Long> {

    Optional<Move> findByExternalId(Long externalId);

    Optional<Move> findByCallId(Long callId);

    List<Move> findAllByIsSent(boolean isSent);

    List<Move> findTop5ByOrderByTimeAndDateOfOperationDesc();
}
