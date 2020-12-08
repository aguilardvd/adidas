package com.example.cities;

import com.example.cities.data.ItineraryResponse;
import com.example.cities.data.Segment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Given an origin city name and a destination city name, this service returns the shortest
 * itinerary in time and the shortest itinerary in connections between these cities
 */
@SpringBootApplication
@RestController
public class CitiesApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication( CitiesApplication.class );

        app.run( args );
    }

    @GetMapping("/itineraries")
    public ItineraryResponse getItineraries(
            @RequestParam(value = "cityOrigin" ) String nameOrigin,
            @RequestParam(value = "cityDestination" ) String nameDestination
            ) {
        return new ItineraryCalculator().getItinerary(nameOrigin, nameDestination);
    }
}
