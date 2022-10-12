package ru.wladyslow.moveList.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.wladyslow.moveList.services.FindNewMovesService;

@Component
@RequiredArgsConstructor
public class SendNewMovesJob {

    private final FindNewMovesService findNewMovesService;

    @Scheduled(fixedRateString ="${bot.recountNewMoves}")
    public void findAndPostNewMoves() {
        findNewMovesService.findNewMovesAndPostThem();
    }
}
