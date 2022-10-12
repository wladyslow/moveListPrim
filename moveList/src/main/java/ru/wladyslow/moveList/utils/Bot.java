package ru.wladyslow.moveList.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wladyslow.moveList.dto.MoveDto;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDateTime = move.getTimeAndDateOfOperation().format(formatter);
        String message = move.getVessel().getName() + " / " +
                move.getVessel().getEngName() + " (" +
                move.getVessel().getImo() + ") "+
                move.getVessel().getFlag().getRusName() + " " +
                move.getVessel().getType() + " " +
                move.getVessel().getAgent().getName() + " " +
                move.getOperation().getName() + " " +
                move.getPointOfOperation().getName() + " " +
                formattedDateTime + " " +
                move.getPilot().getName();
        response.setChatId(this.getChatId());
        response.setText(message);
        System.out.println(message);
        execute(response);
    }
}
