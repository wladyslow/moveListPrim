package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.FlagDto;
import ru.wladyslow.moveList.mapper.FlagMapper;
import ru.wladyslow.moveList.repositories.FlagRepository;
import ru.wladyslow.moveList.services.FlagService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlagServiceImpl implements FlagService {

    private final FlagRepository flagRepository;
    private final FlagMapper flagMapper;

    @Override
    @Transactional
    public Long createFlag(String rusName) {
        val flagDto = new FlagDto(rusName);
        val flag = flagMapper.toEntity(flagDto);
        flagRepository.save(flag);
        return flag.getId();
    }

    @Override
    @Transactional
    public List<FlagDto> findAll() {
        return flagMapper.toDtos(flagRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlagDto> findById(Long id) {
        return flagMapper.toOptional(flagRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlagDto> findByRusName(String rusName) {
        return flagMapper.toOptional(flagRepository.findByRusName(rusName));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlagDto> findByEngName(String engName) {
        return flagMapper.toOptional(flagRepository.findByEngName(engName));
    }

    @Override
    @Transactional
    public FlagDto createOrUpdate(String rusName) {
        val flagDtoOptional = flagRepository.findByRusName(rusName);
        if (flagDtoOptional.isEmpty()) {
            val flagDto = new FlagDto(rusName);
            val flag = flagMapper.toEntity(flagDto);
            flagRepository.save(flag);
            return flagMapper.toDto(flagRepository.findById(flag.getId()).get());
        }
        return flagMapper.toDto(flagDtoOptional.get());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        flagRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String rusName, String engName) {
        flagRepository.findById(id).ifPresent(flag -> {
            flag.setRusName(rusName);
            flag.setEngName(engName);
        });
    }
}
