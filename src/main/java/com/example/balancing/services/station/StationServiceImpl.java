package com.example.balancing.services.station;

import com.example.balancing.entity.Station;
import com.example.balancing.exception.EntityType;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationRepository stationRepository;

    @Override
    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new NotFoundElementException(EntityType.STATION));
    }

    @Override
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station updateStation(Long id, Station station) {
        var existingStation = getStationById(id);
        existingStation.setName(station.getName());
        return createStation(existingStation);
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
