package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.wladyslow.moveList.dto.*;
import ru.wladyslow.moveList.services.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class FindNewMovesServiceImpl implements FindNewMovesService {

    private final AgentService agentService;
    private final FlagService flagService;
    private final IceClassService iceClassService;
    private final MoveService moveService;
    private final OperationService operationService;
    private final PilotService pilotService;
    private final PointService pointService;
    private final VesselService vesselService;
    private final BotService botService;

    @Override
    public VesselDto getVesselDto(String urlEnd) {
        String subPart = "http://skap.pasp.ru/";
        String finalUrl = subPart.concat(urlEnd);
        String name = "";
        String engName = "";
        String imo = "";
        FlagDto flag = new FlagDto();
        String type = "";
        double loa = 0;
        double beam = 0;
        double height = 0;
        double grt = 0;
        double swbt = -1;
        double dwt = 0;
        IceClassDto iceClass = new IceClassDto();
        int yearOfBuilt = 0;
        Long externalId = 0L;
        AgentDto agent = new AgentDto();
        Document docCustomConn = null;
        try {
            docCustomConn = Jsoup.connect(finalUrl)
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tables = docCustomConn.getElementsByClass("table  table-bordered ");
        Element table = tables.first();
        Elements rows = table.select("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                if (cols.get(j).html().contains("Название (полное) :")) {
                    name = cols.get(j).text().split(":")[1].trim().replace("*", "");
                }
                if (cols.get(j).html().contains("Название (анг.) :")) {
                    engName = cols.get(j).text().split(":")[1].trim().replace("*", "");
                }
                if (cols.get(j).html().contains("Код ИМО :")) {
                    imo = cols.get(j).text().split(":")[1].trim();
                }
            }
        }
        Elements tables2 = docCustomConn.getElementsByClass("table  table-hover table-bordered");
        Element table2 = tables2.first();
        Elements rows2 = table2.select("tr");
        for (int i = 0; i < rows2.size(); i++) {
            Element row = rows2.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                if (cols.get(j).html().contains("Флаг :")) {
                    flag = flagService.createOrUpdate(cols.get(j).text().split(":")[1].trim());
                    //flag = new FlagDto(cols.get(j).text().split(":")[1].trim());
                }
                if (cols.get(j).html().contains("Тип :")) {
                    type = cols.get(j).text().split(":")[1].trim();
                }
                if (cols.get(j).html().contains("Агент:")) {
                    agent = agentService.updateOrCreate(cols.get(j).text().split(":")[1].trim());
                    //agent = new AgentDto(cols.get(j).text().split(":")[1].trim());
                }
            }
        }
        Elements tables3 = docCustomConn.getElementsByClass("table table-striped  table-bordered");
        Element table3 = tables3.first();
        Elements rows3 = table3.select("tr");
        for (int i = 0; i < rows3.size(); i++) {
            Element row = rows3.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                if (cols.get(j).html().contains("Длина м.:")) {
                    if (cols.get(j).text().split(":").length > 1) {
                        loa = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                    } else {
                        loa = 0;
                    }
                }
                if (cols.get(j).html().contains("Ширина м.:")) {
                    if (cols.get(j).text().split(":").length > 1) {
                        beam = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                    } else {
                        beam = 0;
                    }
                }
                if (cols.get(j).html().contains("Высота борта м.:")) {
                    if (cols.get(j).text().split(":").length > 1) {
                        height = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                    } else {
                        height = 0;
                    }
                }
            }
        }
        Elements tables4 = docCustomConn.getElementsByClass("table table-striped table-hover table-bordered");
        for (int ind = 0; ind < tables4.size(); ind++) {
            Element table4 = tables4.get(ind);
            Elements rows4 = table4.select("tr");
            for (int i = 0; i < rows4.size(); i++) {
                Element row = rows4.get(i);
                Elements cols = row.select("td");
                for (int j = 0; j < cols.size(); j++) {
                    if (cols.get(j).html().contains("GRT т. :") && grt == 0) {
                        if (cols.get(j).text().split(":").length > 1) {
                            grt = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                        } else {
                            grt = 0;
                        }
                    }
                    if (cols.get(j).html().contains("Исключение из GRT т. :")) {
                        if (cols.get(j).text().split(":").length > 1) {
                            swbt = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                        } else {
                            swbt = 0;
                        }
                    }
                    if (cols.get(j).html().contains("Дедвейт т.:")) {
                        if (cols.get(j).text().split(":").length > 1) {
                            dwt = Double.parseDouble(cols.get(j).text().split(":")[1].trim().replace(",", "."));
                        } else {
                            dwt = 0;
                        }
                    }
                    if (cols.get(j).html().contains("Ледовый класс :")) {
                        iceClass = iceClassService.createOrUpdate(cols.get(j).text().split(":")[1].trim());
                    }
                    if (cols.get(j).html().contains("Год постройки :")) {
                        yearOfBuilt = Integer.parseInt(cols.get(j).text().split(":")[1].trim());
                    }
                    if (cols.get(j).html().contains("№ зап.=")) {
                        externalId = Long.valueOf(cols.get(j).text().split("=")[1].trim());
                    }
                }
            }
        }
        VesselDto vessel = vesselService.updateOrCreate(name, engName, imo,
                flag, type, loa, beam,
                height, grt, swbt, dwt, iceClass,
                yearOfBuilt, externalId, agent);
        return vessel;
    }

    public List<MoveDto> getMovesForSentArrayList(String urlString) {
        List<MoveDto> movesForSent = new ArrayList<>();
        List<MoveDto> moves = getMovesArrayList(urlString);
        for (MoveDto move : moves) {
            if (!move.isSent()) {
                movesForSent.add(move);
            }
        }
        Collections.sort(movesForSent);
        return movesForSent;
    }

    public List<MoveDto> getMovesArrayList(String urlString) {
        List<MoveDto> moves = new ArrayList<>();
        VesselDto vessel = new VesselDto();
        PointDto pointOfOperation = new PointDto();
        OperationDto operation = new OperationDto();
        LocalDateTime timeAndDateOfOperation = null;
        PointDto destinationPoint = new PointDto();
        PilotDto pilot = new PilotDto();
        String operationAtBerth = "";
        Long callId = 0L;
        Long externalId = 0L;
        Document docCustomConn = null;
        try {
            docCustomConn = Jsoup.connect(urlString)
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements pagination = docCustomConn.getElementsByClass("pagination");
        int pagesQuantity = getPageQuantity(pagination.html());
        if (pagesQuantity == 0) {
            Elements tables = docCustomConn.getElementsByClass("table  table-bordered  table-condensed table-hover");
            Element table = tables.first();
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                for (int j = 0; j < cols.size(); j++) {
                    switch (j) {
                        case (0):
                            vessel = getVesselDto(cols.get(j).select("a").first().attr("href"));
                            break;
                        case (1):
                            pointOfOperation = pointService.createOrUpdate(cols.get(j).text());
                            break;
                        case (2):
                            operation = operationService.createOrUpdate(cols.get(j).text());
                            break;
                        case (3):
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
                            LocalDateTime date = LocalDateTime.parse(cols.get(j).text(), formatter);
                            timeAndDateOfOperation = date;
                            break;
                        case (4):
                            destinationPoint = pointService.createOrUpdate(cols.get(j).text());
                            break;
                        case (5):
                            pilot = pilotService.createOrUpdate(cols.get(j).text());
                            break;
                        case (6):
                            operationAtBerth = cols.get(j).text();
                            break;
                        case (8):
                            callId = Long.valueOf(cols.get(j).text());
                            break;
                        case (9):
                            externalId = Long.valueOf(cols.get(j).text());
                            break;
                    }
                }
                MoveDto move = moveService.createOrUpdate(vessel, pointOfOperation, operation,
                        timeAndDateOfOperation, destinationPoint,
                        pilot, operationAtBerth, callId, externalId);
                moves.add(move);
            }

        } else {
            String pagesLink = getPagesLink(pagination.html());

            for (int i = 1; i < pagesQuantity + 1; i++) {
                moves.addAll(getMovesArrayListFromPage(pagesLink.concat(String.valueOf(i))));
            }
        }
        return moves;
    }

    public List<MoveDto> getMovesArrayListFromPage(String urlString) {
        List<MoveDto> moves = new ArrayList<>();
        VesselDto vessel = new VesselDto();
        PointDto pointOfOperation = new PointDto();
        OperationDto operation = new OperationDto();
        LocalDateTime timeAndDateOfOperation = null;
        PointDto destinationPoint = new PointDto();
        PilotDto pilot = new PilotDto();
        String operationAtBerth = "";
        Long callId = 0L;
        Long externalId = 0L;
        Document docCustomConn = null;
        try {
            docCustomConn = Jsoup.connect(urlString)
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tables = docCustomConn.getElementsByClass("table  table-bordered  table-condensed table-hover");
        Element table = tables.first();
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                switch (j) {
                    case (0):
                        vessel = getVesselDto(cols.get(j).select("a").first().attr("href"));
                        break;
                    case (1):
                        pointOfOperation = pointService.createOrUpdate(cols.get(j).text());
                        break;
                    case (2):
                        operation = operationService.createOrUpdate(cols.get(j).text());
                        break;
                    case (3):
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
                        LocalDateTime date = LocalDateTime.parse(cols.get(j).text(), formatter);
                        timeAndDateOfOperation = date;
                        break;
                    case (4):
                        destinationPoint = pointService.createOrUpdate(cols.get(j).text());
                        break;
                    case (5):
                        pilot = pilotService.createOrUpdate(cols.get(j).text());
                        break;
                    case (6):
                        operationAtBerth = cols.get(j).text();
                        break;
                    case (8):
                        callId = Long.valueOf(cols.get(j).text());
                        break;
                    case (9):
                        externalId = Long.valueOf(cols.get(j).text());
                        break;
                }
            }
            MoveDto move = moveService.createOrUpdate(vessel, pointOfOperation, operation,
                    timeAndDateOfOperation, destinationPoint,
                    pilot, operationAtBerth, callId, externalId);
            moves.add(move);
        }
        return moves;
    }


    public static Long extractId(String link) {
        String fin = link.substring(link.indexOf("w/") + 2, link.indexOf("?ha"));
        Long result = Long.valueOf(fin);
        return result;
    }

    public static int getPageQuantity(String paginationStr) {
        int maxPage = 0;
        String[] strArr = paginationStr.split("a href=\"");
        if (strArr.length > 0) {
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i].contains("&amp;page=")) {
                    int pageNumber = Integer.valueOf(strArr[i].substring(strArr[i].lastIndexOf("page=") + 5, strArr[i].indexOf(("\""))));
                    if (pageNumber > maxPage) {
                        maxPage = pageNumber;
                    }
                }
            }
        }
        return maxPage;
    }

    public static String getPagesLink(String paginationStr) {
        String link = "";
        String subString = "http://skap.pasp.ru/";
        String[] strArr = paginationStr.split("a href=\"");
        if (strArr.length > 0) {
            if (strArr[1].contains("&amp;page=")) {
                String endStr = strArr[1].substring(1, strArr[1].indexOf("page=") + 5);
                link = subString.concat(endStr).replaceAll("amp;", "");
            }
        }
        return link;
    }

    @Override
    public void findNewMovesAndPostThem() {
        List<MoveDto> moves = getMovesForSentArrayList("http://skap.pasp.ru/Move/MoveListPaged?harb=PRIM");
        if (moves.size() > 0) {
            for (MoveDto move : moves) {
                if (moves.size() > 5) {
                    botService.sendMessage(move);
                    moveService.messageIsSent(move.getId());
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    botService.sendMessage(move);
                    moveService.messageIsSent(move.getId());
                }
            }
        }
    }
}