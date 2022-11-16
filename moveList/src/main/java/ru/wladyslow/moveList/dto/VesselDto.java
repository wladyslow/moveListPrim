package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

@Data
@NoArgsConstructor
public class VesselDto {

    private Long id;

    private String name;

    private String engName;

    private String aisName;

    private String imo;

    private String mmsi;

    private FlagDto flag;

    private String type;

    private double loa;

    private double beam;

    private double height;

    private double grt;

    private double swbt;

    private double dwt;

    private IceClassDto iceClass;

    private int yearOfBuilt;

    private String nameImo;

    private Long externalId;

    private AgentDto agent;

    private AisDataDto aisData;

    public VesselDto(String name, String engName, String imo, String mmsi,
                     FlagDto flag, String type, double loa, double beam,
                     double height, double grt, double swbt, double dwt, IceClassDto iceClass,
                     int yearOfBuilt, Long externalId, AgentDto agent) {
        this.name = name;
        this.engName = engName;
        this.imo = imo;
        this.mmsi = mmsi;
        this.aisName = this.getVesselsAisName();
        this.flag = flag;
        this.type = type;
        this.loa = loa;
        this.beam = beam;
        this.height = height;
        this.grt = grt;
        this.swbt = swbt;
        this.dwt = dwt;
        this.iceClass = iceClass;
        this.yearOfBuilt = yearOfBuilt;
        this.externalId = externalId;
        this.agent = agent;
        this.nameImo = this.engName + "|" + this.imo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VesselDto vesselDto = (VesselDto) o;
        return Double.compare(vesselDto.loa, loa) == 0 &&
                Double.compare(vesselDto.beam, beam) == 0 &&
                Double.compare(vesselDto.height, height) == 0 &&
                Double.compare(vesselDto.grt, grt) == 0 &&
                Double.compare(vesselDto.swbt, swbt) == 0 &&
                Double.compare(vesselDto.dwt, dwt) == 0 &&
                yearOfBuilt == vesselDto.yearOfBuilt &&
                name.equals(vesselDto.name) &&
                Objects.equals(engName, vesselDto.engName) &&
                imo.equals(vesselDto.imo) &&
                Objects.equals(flag, vesselDto.flag) &&
                Objects.equals(type, vesselDto.type) &&
                Objects.equals(iceClass, vesselDto.iceClass) &&
                Objects.equals(nameImo, vesselDto.nameImo) &&
                externalId.equals(vesselDto.externalId) &&
                Objects.equals(agent, vesselDto.agent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, engName, imo,
                flag, type, loa, beam, height,
                grt, swbt, dwt, iceClass, yearOfBuilt,
                nameImo, externalId, agent);
    }

    public String getVesselFinderLink() {
        String subString = "https://www.vesselfinder.com/vessels/";
        String vesselLink = this.getEngName().replaceAll(" ", "-");
        if (!this.getImo().equals("---")) {
            String imoString = "-IMO-" + this.getImo();
            String link = subString.concat(vesselLink).concat(imoString);
            return "<a href=\"" + link + "\">" + this.getAisName() + "</a>";
        } else if (this.getMmsi() == null || this.getMmsi().equals("---")) {
            return "<b>" + this.getEngName() + "</b>";
        } else {
            String mmsiString = "-IMO-0-MMSI-" + this.getMmsi();
            String link = subString.concat(vesselLink).concat(mmsiString);
            return "<a href=\"" + link + "\">" + this.getAisName() + "</a>";
        }
    }

    public String getVesselFinderUrl() {
        if (this.getEngName() == null) {
            return "unknown request";
        }
        String subString = "https://www.vesselfinder.com/vessels/";
        String vesselLink = this.getEngName().replaceAll(" ", "-");
        if (!this.getImo().equals("---")) {
            String imoString = "-IMO-" + this.getImo();
            return subString.concat(vesselLink).concat(imoString);
        } else if (this.getMmsi() == null || this.getMmsi().equals("---")) {
            return this.getEngName();
        } else {
            String mmsiString = "-IMO-0-MMSI-" + this.getMmsi();
            return subString.concat(vesselLink).concat(mmsiString);
        }
    }


    public String getVesselsAisName() {
        String link = this.getVesselFinderUrl();
        if (!link.equals(this.getEngName()) && !link.equals("unknown request")) {
            Document docCustomConn = null;
            try {
                docCustomConn = Jsoup.connect(link)
                        .userAgent("Yandex")
                        .timeout(5000)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements vesselName = docCustomConn.select("h1.title");
            return vesselName.first().text();
        }
        return this.getEngName();
    }

    public String getAisName() {
        this.aisName = getVesselsAisName();
        return aisName;
    }
}
