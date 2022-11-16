package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.wladyslow.moveList.services.BotService;
import ru.wladyslow.moveList.services.FindVesselsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindVesselsServiceImpl implements FindVesselsService {

    @Override
    public String findAllVesselsByName(String vesselName) {
        List<String> vessels = findAllTankerByName(vesselName, 1);
        String message = "";
        if (vessels.size() == 0) {
            message = "Судно не найдено";
        } else if (vessels.size() == 1) {
            message = "Результат поиска: " + "\n" + vessels.get(0);
        } else {
            message = String.format(
                    "Найдено %d танкеров: ", vessels.size());
            int i = 1;
            for (String vessel : vessels) {
                message = message.concat("\n" + i + ". " + vessel);
            }
        }
        return message;
    }

    public static int getPagesQuantity(String pages) {
        String pagesQuantity = pages.substring(pages.indexOf("/ ") + 2).trim();
        return Integer.parseInt(pagesQuantity);
    }

    public List<String> findAllTankerByName(String tankerName, int page) {
        String tanker = tankerName.replaceAll(" ", "+");
        String urlFirstPart = "https://www.vesselfinder.com/";
        String subString = "";
        if (page > 1) {
            subString = "https://www.vesselfinder.com/vessels?page=2&type=6&name=DELTA";
            subString = subString.replace("page=2", "page=" + page).
                    replace("name=DELTA", "name=" + tanker);
        } else {
            subString = "https://www.vesselfinder.com/vessels?name=DELTA&type=6";
            subString = subString.replace("name=DELTA", "name=" + tanker);
        }
        Document docCustomConn = null;
        try {
            docCustomConn = Jsoup.connect(subString)
                    .userAgent("Yandex")
                    .timeout(5000)
                    .get();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Elements pagination = docCustomConn.getElementsByClass("pagination-controls");
        int pagesQuantity = getPagesQuantity(pagination.first().text());
        List<String> vesselLinks = new ArrayList<>();
        Elements tables = docCustomConn.getElementsByClass("results");
        Element table = tables.first();
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String vesselPart1 = "";
            String vesselPart2 = "";
            String vesUrl = urlFirstPart.concat(cols.get(0).select("a").first().attr("href"));
            vesselPart1 = "<a href=\"" + vesUrl + "\">";
            String name = cols.get(1).getElementsByClass("slna").text();
            vesselPart2 = name + "</a>";
            if (vesselPart1.contains("<a href=") && vesselPart2.contains("</a>")) {
                String vessel = vesselPart1.concat(vesselPart2);
                vesselLinks.add(vessel);
            }
        }
        if (pagesQuantity > 1 && page < pagesQuantity) {
            vesselLinks.addAll(findAllTankerByName(tankerName, page + 1));
        }
        return vesselLinks;
    }

}
