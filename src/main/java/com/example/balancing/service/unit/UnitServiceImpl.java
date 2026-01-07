package com.example.balancing.service.unit;

import com.example.balancing.dto.UnitDto;
import com.example.balancing.entity.Station;
import com.example.balancing.entity.Unit;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.IllegalArgumentException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.StationRepository;
import com.example.balancing.repository.UnitRepository;
import com.example.balancing.service.user.UserService;
import com.example.balancing.transformer.UnitMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final StationRepository stationRepository;
    private final UnitMapper unitMapper;
    private final UserService userService;

    @Transactional
    @Override
    public UnitDto create(UUID stationId, UnitDto dto) {
        User user = userService.getCurrentUser();
        Unit unit = unitMapper.dtoToEntity(dto);
        Station station = stationRepository.findByIdAndUserId(stationId, user.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.STATION));
        unit.setStation(station);
        return unitMapper.entityToDto(unitRepository.save(unit));
    }

    @Transactional
    @Override
    public List<UnitDto> getByStation(UUID stationId) {
        User user = userService.getCurrentUser();
        return unitRepository.findByStationIdAndUserId(stationId, user.getId())
                .stream()
                .map(unitMapper::entityToDto)
                .toList();
    }

    @Transactional
    @Override
    public UnitDto update(UnitDto dto) {
        User user = userService.getCurrentUser();
        Unit existing = unitRepository.findByIdAndUserId(dto.getId(), user.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.UNIT));
        existing.setUnitNumber(dto.getUnitNumber());
        existing.setUnitType(dto.getUnitType());
        existing.setWeightPrecision(dto.getWeightPrecision());
        existing.setWeightUnitMeasure(dto.getWeightUnitMeasure());
        existing.setVibrationPrecision(dto.getVibrationPrecision());
        existing.setVibrationUnitMeasure(dto.getVibrationUnitMeasure());
        existing.setDescription(dto.getDescription());
        return unitMapper.entityToDto(unitRepository.save(existing));
    }

    @Transactional
    @Override
    public void delete(UnitDto dto) {
        User user = userService.getCurrentUser();
        unitRepository.deleteByIdAndUserId(dto.getId(), user.getId());
    }

}
