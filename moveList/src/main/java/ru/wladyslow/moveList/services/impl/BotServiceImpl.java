package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.services.BotService;
import ru.wladyslow.moveList.utils.Bot;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

   private final Bot bot;

    @Override
    public void sendMessage(MoveDto moveDto) {
        try {
            bot.sendMoveInfoMessage(moveDto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
