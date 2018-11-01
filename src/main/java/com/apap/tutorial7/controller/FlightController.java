package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;

    @Autowired
    RestTemplate restTemplate;
    
    @Bean
    public RestTemplate res() {
    	return new RestTemplate();
    }
    
    @PostMapping(value = "/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
    	return flightService.addFlight(flight);
    }

    @PutMapping(value = "/update/{flightId}")
    public String updateFligthSubmit(@PathVariable("flightId") long flightId,
    		@RequestParam("destination") String destination,
    		@RequestParam("origin") String origin,
    		@RequestParam("time") Date time) {
    	FlightModel flight = flightService.getFlightDetailbyId(flightId).get();
    	if(flight.equals(null)) {
    		return "Couldn't find your flight";
    	}
    	flight.setDestination(destination);
    	flight.setOrigin(origin);
    	flight.setTime(time);
    	flightService.updateFlight(flightId, flight);
    	return "success";
    }
    
    @GetMapping(value = "/view/{flightNumber}")
    public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {
    	FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
    	return flight;
    }
    
    @GetMapping(value = "/all")
    public List<FlightModel> flightViewAll(){
    	return flightService.viewAll();
    }
    
    @DeleteMapping(value = "/delete")
    public String deleteFlight(@RequestParam("flightId") long flightId) {
    	FlightModel flight = flightService.getFlightDetailbyId(flightId).get();
    	flightService.deleteFlight(flight);
    	return "success";
    }
    
    @GetMapping(value = "/airports/{city}")
    public String getAirports(@PathVariable("city") String city) throws Exception{
    	String path = Setting.airportUrl + city +"&country=ID";
    	return restTemplate.getForEntity(path, String.class).getBody();
    }
}