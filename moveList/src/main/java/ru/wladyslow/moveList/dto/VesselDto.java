package ru.wladyslow.moveList.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VesselDto {

    private Long id;

    private String name;

    private String engName;

    private String imo;

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

    public VesselDto(String name, String engName, String imo,
                     FlagDto flag, String type, double loa, double beam,
                     double height, double grt, double swbt, double dwt, IceClassDto iceClass,
                     int yearOfBuilt, Long externalId, AgentDto agent) {
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
}