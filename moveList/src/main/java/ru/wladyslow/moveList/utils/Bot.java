package ru.wladyslow.moveList.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.PortScheduleDto;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.chatid}")
    private Long chatId;

    private UpdateReceiver updateReceiver;

    public Bot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

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
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Что-то пошло не так...");
        }
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

