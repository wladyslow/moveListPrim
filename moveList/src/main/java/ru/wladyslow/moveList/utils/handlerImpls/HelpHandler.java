package ru.wladyslow.moveList.utils.handlerImpls;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.utils.Handler;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.TelegramBotUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class HelpHandler implements Handler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message) {
        // Создаем кнопку для смены имени
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                TelegramBotUtil.createInlineKeyboardButton("Изменить ник", "NAME_CHANGE"));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
        SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
        mes.setText(String.format(
                "Нужна помощь %s? Вот она!", user.getName()));
        mes.setReplyMarkup(inlineKeyboardMarkup);
        botReply.add(mes);
        return botReply;
    }

    @Override
    public State operatedBotState() {
        return State.NONE;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
