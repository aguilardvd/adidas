package com.example.demo;

import com.example.demo.data.Segment;
import com.example.demo.database.DatabaseOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This API provides a method to provide other apps a list of itinerary segments
 * for a given origin city
 */
@SpringBootApplication
@RestController
public class ReadCitiesApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication( ReadCitiesApplication.class );
        app.run( args );
    }

    /**
     * Gets all itinerary segments from given origin city
     * @param name the name of the origin city
     * @return A list of itinerary segments
     */
    @GetMapping("/city")
    public List<Segment> getSegments(@RequestParam(value = "cityOrigin" ) String name) {
        /* Get the itinerary segments */
        return DatabaseOperations.fetchCitySegments(name);

    }

}
