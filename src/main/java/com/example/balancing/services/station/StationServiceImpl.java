package com.example.balancing.services.station;

import com.example.balancing.exception.StationNotFoundException;
import com.example.balancing.models.station.Station;
import com.example.balancing.repositories.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StationServiceImpl implements StationService {

    @Autowired
    StationRepository stationRepository;

    @Override
    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(() ->
                new StationNotFoundException("Станции с таким id не найдено."));
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
