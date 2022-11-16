package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.AisDataDto;
import ru.wladyslow.moveList.dto.VesselDto;
import ru.wladyslow.moveList.mapper.AisDataMapper;
import ru.wladyslow.moveList.mapper.VesselMapper;
import ru.wladyslow.moveList.repositories.AisDataRepository;
import ru.wladyslow.moveList.services.AisDataService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AisDataServiceImpl implements AisDataService {

    private final AisDataRepository aisDataRepository;
    private final AisDataMapper aisDataMapper;
    private final VesselMapper vesselMapper;

    @Override
    @Transactional
    public List<AisDataDto> findAll() {
        return aisDataMapper.toDtos(aisDataRepository.findAll());
    }

    @Override
    @Transactional
    public Optional<AisDataDto> findById(Long id) {
        return aisDataMapper.toOptional(aisDataRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<AisDataDto> findByVessel(VesselDto vessel) {
        return aisDataMapper.toOptional(aisDataRepository.findByVessel(vesselMapper.toEntity(vessel)));
    }

    @Override
    @Transactional
    public Optional<AisDataDto> findByVesselId(Long vesselId) {
        return aisDataMapper.toOptional(aisDataRepository.findByVesselId(vesselId));
    }

    @Override
    @Transactional
    public AisDataDto updateOrCreate(Long vesselId, String urlLink) {
        val aisDataDtoOpt = findByVesselId(vesselId);
        if (!aisDataDtoOpt.isEmpty()) {
            val aisDataDtoUpd = getAisDataDto(vesselId, urlLink);
            val aisDataDtoBd = aisDataDtoOpt.get();
            aisDataDtoBd.setCurrentDraft(aisDataDtoUpd.getCurrentDraft());
            aisDataDtoBd.setCurrentSpeed(aisDataDtoUpd.getCurrentSpeed());
            aisDataDtoBd.setEtaDestination(aisDataDtoUpd.getEtaDestination());
            aisDataDtoBd.setEtaDateAndTime(aisDataDtoUpd.getEtaDateAndTime());
            aisDataDtoBd.setNavigationStatus(aisDataDtoUpd.getNavigationStatus());
            aisDataDtoBd.setLastUpdated(aisDataDtoUpd.getLastUpdated());
            aisDataRepository.save(aisDataMapper.toEntity(aisDataDtoBd));
            return aisDataDtoBd;
        }
        val aisDataDtoUpd = getAisDataDto(vesselId, urlLink);
        val aisData = aisDataMapper.toEntity(aisDataDtoUpd);
        aisDataRepository.save(aisData);
        return findByVesselId(vesselId).get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        aisDataRepository.deleteById(id);
    }

    private AisDataDto getAisDataDto(Long vesselId, String urlString) {
        if (urlString.contains("https://www.vesselfinder.com/")) {
            Document docCustomConn = null;
            try {
                docCustomConn = Jsoup.connect(urlString)
                        .userAgent("Yandex")
                        .timeout(5000)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = docCustomConn.select("div.flx._rLk");
            if (!(elements.first() == null)) {
                String eta = elements.first().text();
                Elements aParams = docCustomConn.getElementsByClass("aparams");
                String params = aParams.first().text();
                String etaDest = getEtaDest(eta);
                LocalDateTime etaDateAndTime = getEtaTime(eta);
                String speedString = docCustomConn.getElementsByClass("text2").text();
                double speed = getSpeed(speedString);
                double draft = getDraft(params);
                String navStatus = getNavStatus(params);
                Elements upd = docCustomConn.getElementsByClass("v3 ttt1 valm0");
                String updated = upd.toString().substring(upd.toString().indexOf("data-title=\"") + 12,
                        upd.toString().indexOf("UTC\"") - 1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm", Locale.ENGLISH);
                LocalDateTime dateUpdated = LocalDateTime.parse(updated, formatter).plusHours(3l);
                return new AisDataDto(vesselId, etaDest, etaDateAndTime, navStatus, draft, speed, dateUpdated);
            }
        }
        return new AisDataDto(vesselId, "UNKNOWN", LocalDateTime.now(), "UNKNOWN",
                0, 0, LocalDateTime.now());
    }

    public double getSpeed(String data) {
        if (data.contains("knots")) {
            String speed = data.substring(data.indexOf("speed of ") + 9, data.indexOf(" knots"));
            return Double.valueOf(speed);
        }
        return 0;
    }

    public double getDraft(String data) {
        if (data.contains("draught ")) {
            String draft = data.substring(data.indexOf("draught ") + 8, data.indexOf(" m "));
            return Double.valueOf(draft);
        }
        return 0;
    }

    public String getNavStatus(String data) {
        if (data.contains("Navigation Status ")) {
            return data.substring(data.indexOf("Navigation Status ") + 18, data.indexOf(" Position"));
        }
        return "UNKNOWN";
    }

    public String getEtaDest(String eta) {
        if (eta.contains("Destination not available")) {
            return "Unknown";
        }
        if (eta.contains("ETA")) {
            return eta.split("ETA:")[0].trim();
        } else if (eta.contains("ATA")) {
            return eta.split("ATA:")[0].trim();
        }
        return eta;
    }

    public LocalDateTime getEtaTime(String eta) {
        if (eta.contains("Destination not available")) {
            return LocalDateTime.now();
        }
        String dateAndTime = eta.split("TA:")[1].replaceAll("UTCARRIVED", "").trim();
        String[] dateAndTimeAr = dateAndTime.split(", ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String monthsString = ",Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec";
        String[] months = monthsString.split(",");
        String etaMonth = "";
        int etaYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();
        for (int i = 1; i < 13; i++) {
            if (months[i].equals(dateAndTimeAr[0].split(" ")[0])) {
                etaMonth = String.valueOf(i);
                if ((currentMonth - i) > 8) {
                    etaYear++;
                }
                if (i < 10) {
                    etaMonth = "0".concat(etaMonth);
                }
            }
        }
        String etaDate = dateAndTimeAr[0].split(" ")[1];
        if (Integer.valueOf(etaDate) < 10) {
            etaDate = "0".concat(etaDate);
        }
        LocalDateTime date = LocalDateTime.parse(etaDate + "." + etaMonth + "."
                + etaYear + " " + dateAndTime.split(", ")[1], formatter);
        return date;
    }
}
