package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.PortScheduleDto;
import ru.wladyslow.moveList.services.BotService;
import ru.wladyslow.moveList.utils.Bot;

import java.util.List;

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

    @Override
    public void sendPortScheduleDto(PortScheduleDto portScheduleDto) {
        try {
            bot.sendPortSchedule(portScheduleDto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFoundVessels(List<String> vessels) {
        try {
            bot.sendFoundVessels(vessels);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
