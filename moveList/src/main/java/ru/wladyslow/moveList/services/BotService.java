package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.PortScheduleDto;

import java.util.List;

public interface BotService {

    void sendMessage(MoveDto moveDto);

    void sendPortScheduleDto(PortScheduleDto portScheduleDto);

    void sendFoundVessels(List<String> vessels);
}
