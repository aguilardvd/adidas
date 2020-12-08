package com.example.cities.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class represents the data of a city segment stored in a database
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {

    private String originCity;
    private String destinationCity;
    private long departureTime;
    private long arrivalTime;

    public Segment(String sOriginCity,
                   String sDestinationCity,
                   long lDepartureTime,
                   long lArrivalTime )
    {
        arrivalTime = lArrivalTime;
        departureTime = lDepartureTime;
        destinationCity = sDestinationCity;
        originCity = sOriginCity;
    }

    public Segment( )
    {
        arrivalTime = 0l;
        departureTime = 0l;
        destinationCity = "";
        originCity = "";
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getDuration()
    {
        return Math.abs( arrivalTime - departureTime );
    }

    @Override
    public String toString() {
        return "Segment{" +
                "originCity='" + originCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
