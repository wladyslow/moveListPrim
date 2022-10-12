package ru.wladyslow.moveList.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    private String imo;

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

    public Vessel(String name, String engName, String imo,
                  Flag flag, String type, double loa, double beam,
                  double height, double grt, double swbt, double dwt, IceClass iceClass,
                  int yearOfBuilt, Long externalId, Agent agent) {
        this.name = name;
        this.engName = engName;
        this.imo = imo;
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

    public void setNameImo() {
        this.nameImo = this.engName + "|" + this.imo;
    }

}
