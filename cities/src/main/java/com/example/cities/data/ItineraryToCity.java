package com.example.cities.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This object reprents a node in the Dijkstra diagram. Each node is a city
 * And this object contains the current optimal itineraries.
 * All those itineraries are updated in every Dijkstra iteration where the city
 * is involved
 */
public class ItineraryToCity {

    /* NOde = City name */
    private final String m_sCity;

    /* Current optimal itinerary in time */
    private List<Segment> m_vSegmentsByDuration;
    /* Current optimal itineraries in connections */
    private List<List<Segment>> m_vSegmentsByConnections;

    /* This value tells Dijkstra algorithm that this node is processed */
    private boolean m_bIsProcessed;

    public ItineraryToCity( String sCity )
    {
        m_sCity = sCity;
        m_vSegmentsByDuration = new ArrayList<>();
        m_vSegmentsByConnections = new ArrayList<>();
        m_bIsProcessed = false;
    }

    public String getCity()
    {
        return m_sCity;
    }

    public boolean isCityProcessed()
    {
        return m_bIsProcessed;
    }

    public void setIsProcessed()
    {
        m_bIsProcessed = true;
    }

    public void setSegmentsByDuration(List<Segment> vSegments )
    {
        m_vSegmentsByDuration = new ArrayList<>(vSegments);
    }

    public List<Segment> getSegmentsByDuration()
    {
        return m_vSegmentsByDuration;
    }

    public long getCurrentDuration()
    {
        return m_vSegmentsByDuration
                .stream()
                .sequential()
                .map( Segment::getDuration )
                .mapToLong(Long::longValue)
                .sum();
    }

    public void addSegmentsByConnections(List<List<Segment>> vSegments )
    {
        m_vSegmentsByConnections.addAll( vSegments );
    }

    public List<List<Segment>> getSegmentsByConnections()
    {
        return m_vSegmentsByConnections
                .stream()
                .sequential()
                .map( ArrayList::new )
                .collect(Collectors.toList());
    }

    public int getCurrentNumberOfConnections()
    {
        return m_vSegmentsByConnections
                .stream()
                .findAny()
                .orElseGet(ArrayList::new)
                .size();
    }

    public void clearSegmentsByConnections()
    {
        m_vSegmentsByConnections.clear();
    }

}
