package com.example.cities;

import com.example.cities.data.Segment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.example.cities.data.Constants.GET_CITY_URL;

/**
 * Rest controller that asks the segmentProvider service for the required itinerary
 * segments
 */
@RestController
public class CityDataFetcher {

    /**
     * Gets the itinerary segments for a given origin city name.
     * It asks the segmentProvider service for them
     * @param sOriginName The origin city name
     * @return
     */
    public List<Segment> getSegmentsForCity(String sOriginName )
    {
        Segment[] vSegments = new RestTemplate().
                getForObject(
                        String.format(GET_CITY_URL, sOriginName), Segment[].class);

        return Arrays.asList(vSegments);
    }
}
