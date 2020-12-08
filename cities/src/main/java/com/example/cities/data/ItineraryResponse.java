package com.example.cities.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The goal of this class is to wrap the answer of the user request
 * It gathers the shortest itinerary in time and a list of the shortest
 * itinerary in connections, as there can be more than one itinerary with the same number of connections
 */
public class ItineraryResponse {

    /* The name of the origin city */
    private String m_sOriginCity;

    /* The name of the destination city */
    private String m_sDestinationCity;

    /* List of itinerary segments that represent the shortest path in time */
    private List<Segment> m_shortestInTime;

    /* A setm of list of itinerary segments that each one represent the shortest path in connections */
    private List<List<Segment>> m_vShortestInConnections;

    public ItineraryResponse(String sOriginCity, String sDestinationCity )
    {
        m_sOriginCity = sOriginCity;
        m_sDestinationCity = sDestinationCity;
        m_shortestInTime = null;
        m_vShortestInConnections = new ArrayList<>();
    }

    public String getOriginCity() {
        return m_sOriginCity;
    }

    public String getDestinationCity() {
        return m_sDestinationCity;
    }

    public void setShortestInTime(List<Segment> shortestInTime )
    {
        m_shortestInTime = shortestInTime;
    }

    public List<Segment> getShortestInTime( )
    {
        return m_shortestInTime;
    }

    public void setShortestInConnections( List<List<Segment>> vItineraries )
    {
        m_vShortestInConnections = new ArrayList<>( vItineraries );
    }

    public void addShortestInConnections(List<Segment> itinerary )
    {
        Optional
                .ofNullable(itinerary)
                .ifPresent(m_vShortestInConnections::add);
    }

    public List<List<Segment>> getShortestInConnection()
    {
        return m_vShortestInConnections;
    }

}
