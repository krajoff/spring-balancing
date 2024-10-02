package com.example.balancing.services.station;

import com.example.balancing.models.station.Station;

public interface StationService {

    Station getStationById(Long id);

    Station createStation(Station Station);

    Station updateStation(Long id, Station Station);

    void deleteStation(Long id);
}
