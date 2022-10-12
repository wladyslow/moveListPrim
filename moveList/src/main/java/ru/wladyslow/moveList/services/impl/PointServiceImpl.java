package ru.wladyslow.moveList.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wladyslow.moveList.dto.PilotDto;
import ru.wladyslow.moveList.dto.PointDto;
import ru.wladyslow.moveList.mapper.PointMapper;
import ru.wladyslow.moveList.repositories.PointRepository;
import ru.wladyslow.moveList.services.PointService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointMapper pointMapper;
    private final PointRepository pointRepository;

    @Override
    @Transactional
    public Long createPoint(String name) {
        val pointDto = new PointDto(name);
        val point = pointMapper.toEntity(pointDto);
        pointRepository.save(point);
        return point.getId();
    }

    @Override
    @Transactional
    public List<PointDto> findAll() {
        return pointMapper.toDtos(pointRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointDto> findById(Long id) {
        return pointMapper.toOptional(pointRepository.findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointDto> findByName(String name) {
        return pointMapper.toOptional(pointRepository.findByName(name));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pointRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        pointRepository.findById(id).ifPresent(point -> {
            point.setName(name);
        });
    }

    @Override
    @Transactional
    public PointDto createOrUpdate(String name) {
        val pointDtoOptional = pointRepository.findByName(name);
        if (pointDtoOptional.isEmpty()) {
            val pointDto = new PointDto(name);
            val point = pointMapper.toEntity(pointDto);
            pointRepository.save(point);
            return pointMapper.toDto(pointRepository.findById(point.getId()).get());
        }
        return pointMapper.toDto(pointDtoOptional.get());
    }
}
