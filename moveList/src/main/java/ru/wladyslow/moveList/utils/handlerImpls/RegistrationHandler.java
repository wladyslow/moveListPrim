package ru.wladyslow.moveList.utils.handlerImpls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.services.UserService;
import ru.wladyslow.moveList.utils.Handler;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.TelegramBotUtil;
import ru.wladyslow.moveList.utils.UserStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RegistrationHandler implements Handler {
    //Храним поддерживаемые CallBackQuery в виде констант
    public static final String NAME_ACCEPT = "/enter_name_accept";
    public static final String NAME_CHANGE = "/enter_name";
    public static final String NAME_CHANGE_CANCEL = "/enter_name_cancel";
    public static final String ACTIVATE = "/activate";

    @Value("${bot.activationKey}")
    private String activationKey;

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message) {
        // Проверяем тип полученного события
        if (message.equalsIgnoreCase(NAME_ACCEPT) || message.equalsIgnoreCase(NAME_CHANGE_CANCEL)) {
            return accept(user);
        } else if (message.equalsIgnoreCase(NAME_CHANGE)) {
            return changeName(user);
        } else if (message.startsWith(ACTIVATE)) {
            String key = message.replaceFirst(ACTIVATE, "").trim();
            return activate(user, key);
        }
        return checkName(user, message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> activate(UserDto user, String key) {
        if (key.equals(activationKey)) {
            user.setBotState(State.VALID_USER);
            user.setUserStatus(UserStatus.REGISTERED);
            userService.saveChanges(user);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить последний СГДС", "/get_schedule"));
            List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить 5 последних движений в порту", "/get_5_last_moves"));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne, inlineKeyboardButtonsRowTwo));
            List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
            SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
            mes.setText(String.format(
                    "Активация прошла успешно, Вы авторизованы под ником: %s", user.getName()));
            mes.setReplyMarkup(inlineKeyboardMarkup);
            botReply.add(mes);
            return botReply;
        }
        return changeName(user);
    }


    private List<PartialBotApiMethod<? extends Serializable>> accept(UserDto user) {
        // Если пользователь принял имя - меняем статус и сохраняем
        user.setBotState(State.NONE);
        user.setUserStatus(UserStatus.INITIAL);
        userService.saveChanges(user);

        // Создаем кнопку для начала игры
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                TelegramBotUtil.createInlineKeyboardButton("Пройти активацию", ACTIVATE));
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
        SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
        mes.setText(String.format(
                "Ваш ник: %s", user.getName()));
        mes.setReplyMarkup(inlineKeyboardMarkup);
        botReply.add(mes);
        return botReply;
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkName(UserDto user, String message) {
        // При проверке имени мы превентивно сохраняем пользователю новое имя в базе
        // идея для рефакторинга - добавить временное хранение имени
        user.setName(message);
        userService.saveChanges(user);

        // Делаем кнопку для применения изменений
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                TelegramBotUtil.createInlineKeyboardButton("Подтвердить", NAME_ACCEPT));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
        SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
        mes.setText(String.format(
                "Вы ввели: %s%nЕсли все верно - нажмите кнопку", user.getName()));
        mes.setReplyMarkup(inlineKeyboardMarkup);
        botReply.add(mes);
        return botReply;
    }

    private List<PartialBotApiMethod<? extends Serializable>> changeName(UserDto user) {
        // При запросе изменения имени мы меняем State
        user.setBotState(State.ENTER_NAME);
        userService.saveChanges(user);

        // Создаем кнопку для отмены операции
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                TelegramBotUtil.createInlineKeyboardButton("Отмена", NAME_CHANGE_CANCEL));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
        SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
        mes.setText(String.format(
                "Ваш текщий ник: %s%nВведите новое имя или нажмите кнопку для продолжения", user.getName()));
        mes.setReplyMarkup(inlineKeyboardMarkup);
        botReply.add(mes);
        return botReply;
    }

    @Override
    public State operatedBotState() {
        return State.ENTER_NAME;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(NAME_ACCEPT, NAME_CHANGE, NAME_CHANGE_CANCEL, ACTIVATE);
    }
}
