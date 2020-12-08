package com.example.demo.database;

/**
 * Created by david on 20/09/2020.
 */
public class Constants
{
  /**
   * Connection to the postgreSQL database data
   */
  public static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/postgres";
  public static final String USER = "postgres";
  public static final String PASSWORD = "admin";

  /* Query to obtain all itinerary segments of a city */
  public static final String GET_CITY_SEGMENTS_QUERY = "select * from cities where city = ?;";

  /*Cities table column names */
  public static final String CITY_COLUMN_NAME = "city";
  public static final String DESTINATION_CITY_COLUMN_NAME = "destination_city";
  public static final String DEPARTURE_TIME_COLUMN_NAME = "departure_time";
  public static final String ARRIVAL_TIME_COLUMN_NAME = "arrival_time";

}
