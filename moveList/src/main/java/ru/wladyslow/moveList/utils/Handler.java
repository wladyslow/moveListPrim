package ru.wladyslow.moveList.utils;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.wladyslow.moveList.dto.UserDto;

import java.io.Serializable;
import java.util.List;

public interface Handler {

// основной метод, который будет обрабатывать действия пользователя
    List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message);

    State operatedBotState();
    // метод, который позволяет узнать, какие команды CallBackQuery мы можем обработать в этом классе
    List<String> operatedCallBackQuery();
}

