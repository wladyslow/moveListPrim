package ru.wladyslow.moveList.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.wladyslow.moveList.dto.UserDto;

public class TelegramBotUtil {

    public static SendMessage createMessageTemplate(UserDto user) {
        return createMessageTemplate(user.getChatId());
    }

    // Создаем шаблон SendMessage с включенным Markdown
    public static SendMessage createMessageTemplate(Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.enableMarkdown(true);
        return response;
    }

    // Создаем кнопку
    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(command);
        return inlineKeyboardButton;
    }
}
