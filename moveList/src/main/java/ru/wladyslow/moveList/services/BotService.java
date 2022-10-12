package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.MoveDto;

public interface BotService {

    void sendMessage(MoveDto moveDto);
}
