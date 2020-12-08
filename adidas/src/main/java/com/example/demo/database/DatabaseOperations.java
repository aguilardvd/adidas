package com.example.demo.database;

import com.example.demo.data.Segment;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.database.Constants.*;

/**
 * Class used to fetch values in database
 */
public class DatabaseOperations
{
  /**
   * Private constructor to avoid instances
   */
  private DatabaseOperations(){}

  /**
   * Gets all the itinerary segments of a given city
   * @param sCityName The origin city name to obtain all itinerary segments
   * @return A list of itinerary segments
   */
  public static List<Segment> fetchCitySegments( String sCityName )
  {
    List<Segment> vCitySegments = new ArrayList<>();

    try (Connection con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
         PreparedStatement preparedStatement = con.prepareStatement(GET_CITY_SEGMENTS_QUERY))
    {

      preparedStatement.setString( 1, sCityName );

      ResultSet resultSet = preparedStatement.executeQuery();

      /* Fill the list with the values in the result set */
      while ( resultSet.next() )
      {
        String sOriginCity = resultSet.getString(CITY_COLUMN_NAME);
        String sDestinationCity = resultSet.getString(DESTINATION_CITY_COLUMN_NAME);
        long lDepartureTime = resultSet.getLong(DEPARTURE_TIME_COLUMN_NAME);
        long lArrivalTime = resultSet.getLong(ARRIVAL_TIME_COLUMN_NAME);

        vCitySegments.add( new Segment( sOriginCity, sDestinationCity, lDepartureTime, lArrivalTime ) );
      }

    } catch (SQLException ex)
    {
      System.out.println(ex.getStackTrace());
    }
    return vCitySegments;
  }
}
