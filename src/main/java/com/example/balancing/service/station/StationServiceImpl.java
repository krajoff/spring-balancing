package com.example.balancing.service.station;

import com.example.balancing.dto.StationDto;
import com.example.balancing.entity.Station;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.StationRepository;
import com.example.balancing.service.user.UserService;
import com.example.balancing.transformer.StationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@AllArgsConstructor
@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;
    private final UserService userService;

    @Transactional
    @Override
    public StationDto create(StationDto dto) {
        User user = userService.getCurrentUser();
        Station station = stationMapper.dtoToEntity(dto);
        station.setUser(user);
        return stationMapper.entityToDto(stationRepository.save(station));
    }

    @Transactional
    @Override
    public List<StationDto> getAllByUser() {
        User user = userService.getCurrentUser();
        List<Station> entities = stationRepository.findAllByUserId(user.getId());
        return entities
                .stream()
                .map(stationMapper::entityToDto)
                .toList();
    }

    @Transactional
    @Override
    public StationDto update(StationDto source) {
        User user = userService.getCurrentUser();
        Station station = stationRepository
                .findByIdAndUserId(source.getId(), user.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.STATION));
        if (StringUtils.hasText(source.getName())) station.setName(source.getName());
        return stationMapper.entityToDto(stationRepository.save(station));
    }

    @Transactional
    @Override
    public void delete(StationDto source) {
        User user = userService.getCurrentUser();
        Station station = stationRepository
                .findByIdAndUserId(source.getId(), user.getId())
                .orElseThrow(() -> new NotFoundElementException(EntityTypeException.STATION));
        stationRepository.delete(station);
    }

}
