package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.MoveDto;

import java.util.List;

public interface FindLastFiveMoves {

    List<MoveDto> getMoveDtoListForBot();
}
