package com.apap.tutorial7.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial7.model.FlightModel;

/**
 * FlightService
 */
public interface FlightService {
    FlightModel addFlight(FlightModel flight);
    
    void deleteByFlightNumber(String flightNumber);

    Optional<FlightModel> getFlightDetailByFlightNumber(String flightNumber);
    
    Optional<FlightModel> getFlightDetailbyId(long id);
    
    List<FlightModel> viewAll();
    
    void deleteFlight(FlightModel flight);
    
    FlightModel updateFlight(long flightId, FlightModel flight);
}