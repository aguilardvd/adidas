package com.example.demo.data;

/**
 * This class represents the data of a city segment stored in a database
 */
public class Segment {

    private final String m_sOriginCity;
    private final String m_sDestinationCity;
    private final long m_lDepartureTime;
    private final long m_lArrivalTime;

    public Segment( String sOriginCity,
            String sDestinationCity,
            long lDepartureTime,
            long lArrivalTime )
    {
        m_lArrivalTime = lArrivalTime;
        m_lDepartureTime = lDepartureTime;
        m_sDestinationCity = sDestinationCity;
        m_sOriginCity = sOriginCity;
    }

    public String getOriginCity() {
        return m_sOriginCity;
    }

    public String getDestinationCity() {
        return m_sDestinationCity;
    }

    public long getDepartureTime() {
        return m_lDepartureTime;
    }

    public long getArrivalTime() {
        return m_lArrivalTime;
    }

    public long getDuration()
    {
        return Math.abs( m_lArrivalTime - m_lDepartureTime );
    }
}
