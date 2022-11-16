package ru.wladyslow.moveList.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.io.IOException;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "VESSEL")
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String engName;

    @Column
    private String aisName;

    @Column
    private String imo;

    @Column
    private String mmsi;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "FLAG_ID")
    private Flag flag;

    @Column
    private String type;

    @Column
    private double loa;

    @Column
    private double beam;

    @Column
    private double height;

    @Column
    private double grt;

    @Column
    private double swbt;

    @Column
    private double dwt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ICE_CLASS_ID")
    private IceClass iceClass;

    @Column
    private int yearOfBuilt;

    @Column
    private String nameImo;

    @Column
    private Long externalId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "AIS_DATA_ID", referencedColumnName = "id")
    private AisData aisData;

    public Vessel(String name, String engName, String imo, String mmsi,
                  Flag flag, String type, double loa, double beam,
                  double height, double grt, double swbt, double dwt, IceClass iceClass,
                  int yearOfBuilt, Long externalId, Agent agent, AisData aisData) {
        this.name = name;
        this.engName = engName;
        this.imo = imo;
        this.mmsi = mmsi;
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
        this.aisData = aisData;
        this.nameImo = this.engName + "|" + this.imo;
        this.aisName = getVesselsAisName();
    }

    public void setNameImo() {
        this.nameImo = this.engName + "|" + this.imo;
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
}
