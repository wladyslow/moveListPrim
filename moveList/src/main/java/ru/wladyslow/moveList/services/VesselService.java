package ru.wladyslow.moveList.services;

import ru.wladyslow.moveList.dto.*;

import java.util.List;
import java.util.Optional;

public interface VesselService {

    Long createVessel(String name, String engName, String imo, String mmsi,
                      FlagDto flag, String type, double loa, double beam,
                      double height, double grt, double swbt, double dwt, IceClassDto iceClass,
                      int yearOfBuilt, Long externalId, AgentDto agent);

    List<VesselDto> findAll();

    Optional<VesselDto> findById(Long id);

    Optional<VesselDto> findByName(String name);

    Optional<VesselDto> findByEngName(String engName);

    Optional<VesselDto> findByExternalId(Long externalId);

    Optional<VesselDto> findByNameImo(String nameImo);

    void deleteById(Long id);

    void update(Long id, String name, String engName, String imo, String mmsi,
                FlagDto flag, String type, double loa, double beam,
                double height, double grt, double swbt, double dwt, IceClassDto iceClass,
                int yearOfBuilt, Long externalId, AgentDto agent);

    VesselDto updateOrCreate(String name, String engName, String imo, String mmsi,
                             FlagDto flag, String type, double loa, double beam,
                             double height, double grt, double swbt, double dwt, IceClassDto iceClass,
                             int yearOfBuilt, Long externalId, AgentDto agent);
}
