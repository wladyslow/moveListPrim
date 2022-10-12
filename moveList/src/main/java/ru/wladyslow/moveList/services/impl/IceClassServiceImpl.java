package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.FlagDto;
import ru.wladyslow.moveList.dto.IceClassDto;
import ru.wladyslow.moveList.mapper.IceClassMapper;
import ru.wladyslow.moveList.repositories.IceClassRepository;
import ru.wladyslow.moveList.services.IceClassService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IceClassServiceImpl implements IceClassService {

    private final IceClassRepository iceClassRepository;
    private final IceClassMapper iceClassMapper;

    @Override
    @Transactional
    public Long createIceClass(String name) {
        val iceClassDto = new IceClassDto(name);
        val iceClass = iceClassMapper.toEntity(iceClassDto);
        iceClassRepository.save(iceClass);
        return iceClass.getId();
    }

    @Override
    @Transactional
    public List<IceClassDto> findAll() {
        return iceClassMapper.toDtos(iceClassRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IceClassDto> findById(Long id) {
        return iceClassMapper.toOptional(iceClassRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IceClassDto> findByName(String name) {
        return iceClassMapper.toOptional(iceClassRepository.findByName(name));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        iceClassRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        iceClassRepository.findById(id).ifPresent(iceClass -> {
            iceClass.setName(name);
        });
    }

    @Override
    @Transactional
    public IceClassDto createOrUpdate(String name) {
        val iceClassDtoOptional = iceClassRepository.findByName(name);
        if (iceClassDtoOptional.isEmpty()) {
            val iceClassDto = new IceClassDto(name);
            val iceClass = iceClassMapper.toEntity(iceClassDto);
            iceClassRepository.save(iceClass);
            return iceClassMapper.toDto(iceClassRepository.findById(iceClass.getId()).get());
        }
        return iceClassMapper.toDto(iceClassDtoOptional.get());
    }
}
