package ru.wladyslow.moveList.utils;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.wladyslow.moveList.dto.UserDto;

import java.io.Serializable;
import java.util.List;

public interface Handler {

    List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message);

    State operatedBotState();
    List<String> operatedCallBackQuery();
}

