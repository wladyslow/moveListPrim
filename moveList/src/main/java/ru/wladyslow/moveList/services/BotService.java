package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.PortScheduleDto;

public interface BotService {

    void sendMessage(MoveDto moveDto);

    void sendPortScheduleDto(PortScheduleDto portScheduleDto);
}
