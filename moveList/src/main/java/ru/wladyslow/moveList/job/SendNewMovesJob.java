package ru.wladyslow.moveList.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.wladyslow.moveList.services.FindNewMovesService;
import ru.wladyslow.moveList.services.FindNewPortSchedule;
import ru.wladyslow.moveList.utils.Bot;

@Component
@RequiredArgsConstructor
public class SendNewMovesJob {

    private final FindNewMovesService findNewMovesService;
    private final FindNewPortSchedule findNewPortSchedule;
    private final Bot bot;

    @Scheduled(fixedRateString ="${bot.recountNewMoves}")
    public void findAndPostNewMoves() {
        findNewMovesService.findNewMovesAndPostThem();
    }

    @Scheduled(fixedRateString ="${bot.recountNewSchedule}")
    public void findAndPostNewPortSchedule() {
        findNewPortSchedule.findNewPortScheduleAndPostIt();
    }

}
