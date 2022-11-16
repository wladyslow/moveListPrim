package ru.wladyslow.moveList.utils.handlerImpls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.wladyslow.moveList.dto.MoveDto;
import ru.wladyslow.moveList.dto.UserDto;
import ru.wladyslow.moveList.services.FindLastFiveMoves;
import ru.wladyslow.moveList.services.FindLastPortSchedule;
import ru.wladyslow.moveList.services.FindVesselsService;
import ru.wladyslow.moveList.utils.Handler;
import ru.wladyslow.moveList.utils.State;
import ru.wladyslow.moveList.utils.TelegramBotUtil;
import ru.wladyslow.moveList.utils.UserStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class RequestHandler implements Handler {

    public static final String GET_SCHEDULE = "/get_schedule";
    public static final String GET_5_LAST_MOVES = "/get_5_last_moves";
    private final FindLastPortSchedule findLastPortSchedule;
    private final FindLastFiveMoves findLastFiveMoves;
    private final FindVesselsService findVesselsService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserDto user, String message) {
        if (message.startsWith(GET_SCHEDULE)) {
            return getSchedule(user);
        } else if (message.startsWith(GET_5_LAST_MOVES)) {
            return getLastFiveMoves(user);
        } else if(message.startsWith("/f_")){
            log.info(message);
            String vesselName = message.replace("/f_", "");
            log.info(vesselName);
            return findVessels(user, vesselName);
        }
        return null;
    }

    private List<PartialBotApiMethod<? extends Serializable>> getSchedule(UserDto user) {
        if (user.getUserStatus().equals(UserStatus.REGISTERED)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить последний СГДС", "/get_schedule"));
            List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить 5 последних движений в порту", "/get_5_last_moves"));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne, inlineKeyboardButtonsRowTwo));
            List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
            SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
            mes.setText(findLastPortSchedule.getPortScheduleForBot().getScheduleMessage());
            mes.setReplyMarkup(inlineKeyboardMarkup);
            mes.enableHtml(true);
            mes.disableWebPagePreview();
            botReply.add(mes);
            return botReply;
        }
        return null;
    }

    private List<PartialBotApiMethod<? extends Serializable>> findVessels(UserDto user, String vesselName) {
        if (user.getUserStatus().equals(UserStatus.REGISTERED)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить последний СГДС", "/get_schedule"));
            List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить 5 последних движений в порту", "/get_5_last_moves"));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne, inlineKeyboardButtonsRowTwo));
            List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
            String message = findVesselsService.findAllVesselsByName(vesselName);
            log.info(message);
            SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
            mes.setText(message);
            mes.setReplyMarkup(inlineKeyboardMarkup);
            mes.enableHtml(true);
            mes.disableWebPagePreview();
            botReply.add(mes);
            return botReply;
        }
        return null;
    }

    private List<PartialBotApiMethod<? extends Serializable>> getLastFiveMoves(UserDto user) {
        if (user.getUserStatus().equals(UserStatus.REGISTERED)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить последний СГДС", "/get_schedule"));
            List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo = List.of(
                    TelegramBotUtil.createInlineKeyboardButton("Получить 5 последних движений в порту", "/get_5_last_moves"));
            inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne, inlineKeyboardButtonsRowTwo));
            List<PartialBotApiMethod<? extends Serializable>> botReply = new ArrayList<>();
            String message = "5 последних действий в порту:";
            List<MoveDto> moveDtoList = findLastFiveMoves.getMoveDtoListForBot();
            log.info("Количество мувов: " + moveDtoList.size());
            int i = 1;
            for (MoveDto m : moveDtoList) {
                message = message.concat("\n" + i + ". " +m.getMoveDtoMessage());
                i++;
            }
            SendMessage mes = TelegramBotUtil.createMessageTemplate(user);
            mes.setText(message);
            mes.setReplyMarkup(inlineKeyboardMarkup);
            mes.enableHtml(true);
            mes.disableWebPagePreview();
            botReply.add(mes);
            return botReply;
        }
        return null;
    }


    @Override
    public State operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(GET_SCHEDULE, GET_5_LAST_MOVES, "f_");
    }
}
