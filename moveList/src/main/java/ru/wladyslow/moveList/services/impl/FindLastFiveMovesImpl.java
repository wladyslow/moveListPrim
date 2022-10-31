package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.services.FindLastFiveMoves;
import ru.wladyslow.moveList.services.MoveService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindLastFiveMovesImpl implements FindLastFiveMoves {

    private final MoveService moveService;

    @Override
    public List<MoveDto> getMoveDtoListForBot() {
        return moveService.findLastFiveMoves();
    }
}
