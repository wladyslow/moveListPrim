package ru.wladyslow.moveList.utils.handlerImpls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.services.UserService;
import ru.wladyslow.moveList.utils.Handler;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.TelegramBotUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class StartHandler implements Handler {

    @Value("${bot.name}")
    private String botUsername;

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message) {
        SendMessage welcomeMessage = TelegramBotUtil.createMessageTemplate(user);
        welcomeMessage.setText(String.format(
                "Привет! Я *%s*%n", botUsername
        ));
        SendMessage registrationMessage = TelegramBotUtil.createMessageTemplate(user);
        registrationMessage.setText("Для начала работы сообщите Ваше имя, пожалуйста.");
        user.setBotState(State.ENTER_NAME);
        userService.saveChanges(user);
        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
