package ru.wladyslow.moveList.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wladyslow.moveList.entities.PortSchedule;

import java.time.LocalDate;
import java.util.Optional;

public interface PortScheduleRepository extends JpaRepository<PortSchedule, Long> {
    Optional<PortSchedule> findByScheduleDate(LocalDate scheduleDate);
}
