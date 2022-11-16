package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.wladyslow.moveList.dto.*;
import ru.wladyslow.moveList.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindNewPortScheduleServiceImpl implements FindNewPortSchedule {

    private final FindNewMovesService findNewMovesService;
    private final AgentService agentService;
    private final PortScheduleService portScheduleService;
    private final ScheduledOperationService scheduledOperationService;
    private final BotService botService;

    @Override
    public void findNewPortScheduleAndPostIt() {
        val optionalPortScheduleDto = getPortScheduleDtoOpt();
        if (!optionalPortScheduleDto.isEmpty()) {
            botService.sendPortScheduleDto(optionalPortScheduleDto.get());
        }
    }

    public Optional<PortScheduleDto> getPortScheduleDtoOpt() {
        if (LocalDateTime.now().getHour() > 11) {
            String subLink = "http://skap.pasp.ru/Plan/SgdsDayListPrev?Harb=PRIM&PlanDate=";
            LocalDate scheduleDate = LocalDate.now().plusDays(1);
            LocalTime time = LocalTime.of(0,0);
            LocalDateTime dateTime = LocalDateTime.of(scheduleDate,time);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            String link = subLink.concat(formattedDateTime);
            String scheduledOperationName = "";
            LocalDateTime timeAndDateOfScheduledOperation = LocalDateTime.now();
            VesselDto vessel = new VesselDto();
            AgentDto agent = new AgentDto();
            String route = "";
            String pilotCompanyName = "";
            Long externalId = 0L;
            List<ScheduledOperationDto> scheduledOperationDtos = new ArrayList<>();
            Document docCustomConn = null;
            try {
                docCustomConn = Jsoup.connect(link)
                        .userAgent("Mozilla")
                        .timeout(5000)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements tables = docCustomConn.getElementsByClass("table  table-bordered  table-condensed table-hover");
            String tableName = tables.tagName("caption").first().text();
            DateTimeFormatter formatterOfDates = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter formatterDateAndTimeOfScheduledOp = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            String dateAndHead = tableName.split("Приморск: Плановый день ")[1].substring(0, 10);
            LocalDate dateOfSchedule = LocalDate.parse(dateAndHead, formatterOfDates);
            Element table = tables.first();
            Elements rows = table.select("tr");
            if (rows.size() > 1) {
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    for (int j = 0; j < cols.size(); j++) {
                        switch (j) {
                            case (0):
                                scheduledOperationName = cols.get(j).text();
                                break;
                            case (1):
                                timeAndDateOfScheduledOperation = LocalDateTime.parse(cols.get(j).text(), formatterDateAndTimeOfScheduledOp);
                                break;
                            case (2):
                                vessel = findNewMovesService.getVesselDto(cols.get(j).select("a").first().attr("href"));
                                break;
                            case (5):
                                agent = agentService.updateOrCreate(cols.get(j).text());
                                break;
                            case (9):
                                route = cols.get(j).text();
                                break;
                            case (10):
                                pilotCompanyName = cols.get(j).text();
                                break;
                            case (11):
                                externalId = Long.valueOf(cols.get(j).text());
                                break;
                        }
                    }
                    ScheduledOperationDto scheduledOperationDto = new ScheduledOperationDto(scheduledOperationName, timeAndDateOfScheduledOperation,
                            vessel, agent, route, pilotCompanyName, externalId);
                    scheduledOperationDtos.add(scheduledOperationDto);
                }
                if (!portScheduleService.findByScheduleDate(dateOfSchedule).isEmpty()) {
                    if (!new PortScheduleDto(dateOfSchedule, scheduledOperationDtos).getScheduleMessage().equals(portScheduleService.findByScheduleDate(dateOfSchedule).get().getScheduleMessage())) {
                        portScheduleService.findByScheduleDate(dateOfSchedule).get().getScheduledOperations().forEach(scheduledOperationDto -> {
                            scheduledOperationService.deleteById(scheduledOperationDto.getId());
                        });
                        portScheduleService.createOrUpdate(dateOfSchedule, scheduledOperationDtos);
                        return portScheduleService.findByScheduleDate(dateOfSchedule);
                    } else {
                        return Optional.empty();
                    }
                } else {
                    portScheduleService.createOrUpdate(dateOfSchedule, scheduledOperationDtos);
                    return portScheduleService.findByScheduleDate(dateOfSchedule);
                }
            }
        }
        return Optional.empty();
    }
}
