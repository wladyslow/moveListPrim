package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.AgentDto;
import ru.wladyslow.moveList.dto.FlagDto;
import ru.wladyslow.moveList.dto.IceClassDto;
import ru.wladyslow.moveList.dto.VesselDto;
import ru.wladyslow.moveList.mapper.AgentMapper;
import ru.wladyslow.moveList.mapper.FlagMapper;
import ru.wladyslow.moveList.mapper.IceClassMapper;
import ru.wladyslow.moveList.mapper.VesselMapper;
import ru.wladyslow.moveList.repositories.VesselRepository;
import ru.wladyslow.moveList.services.VesselService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService {

    private final VesselMapper vesselMapper;
    private final VesselRepository vesselRepository;
    private final FlagMapper flagMapper;
    private final IceClassMapper iceClassMapper;
    private final AgentMapper agentMapper;

    @Override
    @Transactional
    public Long createVessel(String name, String engName,
                             String imo, FlagDto flag, String type,
                             double loa, double beam, double height,
                             double grt, double swbt, double dwt, IceClassDto iceClass,
                             int yearOfBuilt, Long externalId, AgentDto agent) {
        val vesselDto = new VesselDto(name, engName,
                imo, flag, type,
                loa, beam, height,
                grt, swbt, dwt, iceClass,
                yearOfBuilt, externalId, agent);
        val vessel = vesselMapper.toEntity(vesselDto);
        vesselRepository.save(vessel);
        return vessel.getId();
    }

    @Override
    @Transactional
    public List<VesselDto> findAll() {
        return vesselMapper.toDtos(vesselRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDto> findById(Long id) {
        return vesselMapper.toOptional(vesselRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDto> findByName(String name) {
        return vesselMapper.toOptional(vesselRepository.findByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDto> findByEngName(String engName) {
        return vesselMapper.toOptional(vesselRepository.findByEngName(engName));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDto> findByExternalId(Long externalId) {
        return vesselMapper.toOptional(vesselRepository.findByExternalId(externalId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDto> findByNameImo(String nameImo) {
        return vesselMapper.toOptional(vesselRepository.findByNameImo(nameImo));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        vesselRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name, String engName,
                       String imo, FlagDto flag, String type,
                       double loa, double beam, double height,
                       double grt, double swbt, double dwt,
                       IceClassDto iceClass, int yearOfBuilt,
                       Long externalId, AgentDto agent) {
        vesselRepository.findById(id).ifPresent(vessel -> {
            vessel.setName(name);
            vessel.setEngName(engName);
            vessel.setImo(imo);
            vessel.setFlag(flagMapper.toEntity(flag));
            vessel.setType(type);
            vessel.setLoa(loa);
            vessel.setBeam(beam);
            vessel.setHeight(height);
            vessel.setGrt(grt);
            vessel.setSwbt(swbt);
            vessel.setDwt(dwt);
            vessel.setIceClass(iceClassMapper.toEntity(iceClass));
            vessel.setYearOfBuilt(yearOfBuilt);
            vessel.setExternalId(externalId);
            vessel.setAgent(agentMapper.toEntity(agent));
        });
    }

    @Override
    @Transactional
    public VesselDto updateOrCreate(String name, String engName, String imo,
                                    FlagDto flag, String type, double loa,
                                    double beam, double height, double grt,
                                    double swbt, double dwt, IceClassDto iceClass,
                                    int yearOfBuilt, Long externalId, AgentDto agent) {
        val vesselOptional = vesselMapper.toOptional(vesselRepository.findByExternalId(externalId));
        if (vesselOptional.isEmpty()) {
            val vesselDto = new VesselDto(name, engName,
                    imo, flag, type,
                    loa, beam, height,
                    grt, swbt, dwt, iceClass,
                    yearOfBuilt, externalId, agent);
            val vessel = vesselMapper.toEntity(vesselDto);
            vesselRepository.save(vessel);
            return vesselMapper.toDto(vesselRepository.findById(vessel.getId()).get());
        } else {
            vesselRepository.findByExternalId(externalId).ifPresent(vessel -> {
                vessel.setName(name);
                vessel.setEngName(engName);
                vessel.setImo(imo);
                vessel.setFlag(flagMapper.toEntity(flag));
                vessel.setType(type);
                vessel.setLoa(loa);
                vessel.setBeam(beam);
                vessel.setHeight(height);
                vessel.setGrt(grt);
                vessel.setSwbt(swbt);
                vessel.setDwt(dwt);
                vessel.setIceClass(iceClassMapper.toEntity(iceClass));
                vessel.setYearOfBuilt(yearOfBuilt);
                vessel.setAgent(agentMapper.toEntity(agent));
            });
            return vesselMapper.toDto(vesselRepository.findById(vesselOptional.get().getId()).get());
        }
    }
}
