package ru.wladyslow.moveList.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.dto.ScheduledOperationDto;

import java.time.format.DateTimeFormatter;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.chatid}")
    private Long chatId;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public Long getChatId() {
        return chatId;
    }

    @Override
    public void onUpdateReceived(Update update) {
        new SendMessage().setChatId(update.getMessage().getChatId().toString());
    }

    public void sendMoveInfoMessage(MoveDto move) throws TelegramApiException {
        SendMessage response = new SendMessage();
        response.setChatId(this.getChatId());
        response.enableHtml(true);
        response.setText(move.getMoveDtoMessage());
        execute(response);
    }

    public void sendPortSchedule(PortScheduleDto portScheduleDto) throws TelegramApiException {
        SendMessage response = new SendMessage();
        String mes = portScheduleDto.getScheduleMessage();
        response.setChatId(this.getChatId());
        response.enableHtml(true);
        response.setText(mes);
        response.disableWebPagePreview();
        execute(response);
    }
}

