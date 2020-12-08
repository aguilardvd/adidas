package com.example.cities;

import com.example.cities.data.ItineraryToCity;
import com.example.cities.data.ItineraryResponse;
import com.example.cities.data.Segment;

import java.util.*;

/**
 * Provides methods to calculate the shortest itineraries between the two cities
 * It uses the Dijkstra algorithm in order to obtain these optimal itineraries.
 * Given a node origin, the origin city, it calculates using Dijkstra iterations the optimal
 * itinerary to the adjacent nodes (cities). Each node is processed once and in each iteration
 * all adjacent nodes itineraries are updated.
 * The nodes are added to the algorithm as soon as they appear as a new adjacent node.
 * The algorithm stops when all nodes (cities) in the list are processed, but the destination one.
 * At this point, no new city is about to be added as new node, and the algorithm processes the
 * destination city.
 * At this point, all cities have their optimal itinerary, and the result is just
 * the itinerary corresponding to the destination city.
 *
 * For more information: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 */
public class ItineraryCalculator {

    /* Asks for the itinerary segments */
    private CityDataFetcher m_cityDataFetcher;

    public ItineraryCalculator()
    {
        m_cityDataFetcher = new CityDataFetcher();
    }

    /**
     * Calculates all the optimal itineraries
     * @param sOriginCity The origin city name
     * @param sDestinationCity The destination city name
     * @return The optimal itineraries in the wrapped response
     */
    public ItineraryResponse getItinerary(String sOriginCity, String sDestinationCity )
    {
        /* At the begining, only two nodes in the algorith, origin and destination */
        HashMap<String, ItineraryToCity> hItineraries = new HashMap();
        hItineraries.put( sOriginCity, new ItineraryToCity(sOriginCity) );
        hItineraries.put( sDestinationCity,  new ItineraryToCity(sDestinationCity) );

        /* Apply dijkstra algorithm */
        applyDijkstra( hItineraries, sDestinationCity );

        /* Remove the itinerary to origin city */
        hItineraries.remove( sOriginCity );

        /* Wrap the response */
        return Optional
                .ofNullable( hItineraries )
                .map(h -> createItineraryResponse( sOriginCity, sDestinationCity, hItineraries ) )
                .orElse(null);

    }

    /**
     * Applies the dijkstra algorithm
     * @param hItineraries Current dijkstra nodes.
     * @param sDestinationCity The destination city name
     */
    private void applyDijkstra( HashMap<String, ItineraryToCity> hItineraries, String sDestinationCity )
    {
        /* Apply dijkstra to a node until all of them are processed */
        while( !areAllItinerariesProcessed( hItineraries ) )
        {
            /* Get next city node to process */
            String sNextCityToProcess = getNextCityToProcess( hItineraries, sDestinationCity );

            /* Get the current itinerary of the city being processed */
            ItineraryToCity itineraryToCityToProcess = Optional
                    .ofNullable( sNextCityToProcess )
                    .map(hItineraries::get)
                    .orElse(null);

            /* Apply dijkstra iteration to this city node */
            applyDijkstraToCityNode( sNextCityToProcess, hItineraries );

            /* Set the city node processed */
            Optional
                    .ofNullable( itineraryToCityToProcess )
                    .ifPresent( ItineraryToCity::setIsProcessed );
        }
    }


    /**
     * Applies a dijkstra iteration to a city node
     * @param sCityNode The city node name
     * @param hItineraries Current itineraries in system
     */
    private void applyDijkstraToCityNode( String sCityNode, HashMap<String, ItineraryToCity> hItineraries )
    {
        /* Fetch the segments for this city node*/
        List<Segment> vSegmentsWhereDestinationCityIs = Optional
                .ofNullable( sCityNode )
                .map(m_cityDataFetcher::getSegmentsForCity)
                .orElseGet( ArrayList::new );

        /* If there are new city nodes in system, add them to the map */
        vSegmentsWhereDestinationCityIs
                .stream()
                .sequential()
                .map(Segment::getDestinationCity)
                .filter(sCity -> hItineraries.containsKey(sCity) == false)
                .forEach(sCity -> hItineraries.put(sCity, new ItineraryToCity(sCity)));

        /* Apply dijkstra for each received itinerary segment for this city node */
        vSegmentsWhereDestinationCityIs
                .stream()
                .sequential()
                .filter(Objects::nonNull)
                /* Check that the destination city is not an already processed node */
                .filter( segment -> hItineraries.get(segment.getDestinationCity()).isCityProcessed() == false )
                .forEach( segment -> applyDijkstraToCityForSegment( sCityNode, hItineraries, segment ) );
    }

    /**
     * Apply dijkstra for each received itinerary segment for this city node
     * @param sCityNode The city node name
     * @param hItineraries Updated itineraries in system
     * @param segment The itinerary segment
     */
    private void applyDijkstraToCityForSegment( String sCityNode, HashMap<String, ItineraryToCity> hItineraries, Segment segment )
    {
        /* Apply dijkstra for duration */
        applyDijkstraToCityForSegmentInDuration( sCityNode, hItineraries, segment );
        /* Apply dijkstra for connections */
        applyDijkstraToCityForSegmentInConnections( sCityNode, hItineraries, segment );
    }

    /**
     * Apply dijkstra for each received itinerary segment for this city node. Uses duration as criteria
     * For mor info: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * @param sCityNode The city node
     * @param hItineraries Updated itineraries
     * @param segment Current itinerary segment
     */
    private void applyDijkstraToCityForSegmentInDuration( String sCityNode, HashMap<String, ItineraryToCity> hItineraries, Segment segment )
    {
        ItineraryToCity currentItineraryToCityNode = Optional
                .ofNullable( sCityNode )
                .map(hItineraries::get)
                .orElse(null);

        ItineraryToCity currentItineraryToDestination = Optional
                .ofNullable( segment )
                .map(Segment::getDestinationCity)
                .map(hItineraries::get)
                .orElse(null);

        long lCurrentDurationToDestinationCity = Optional
                .ofNullable( currentItineraryToDestination )
                .filter( itineraryToCity -> itineraryToCity.getSegmentsByDuration().size() != 0 )
                .map( ItineraryToCity::getCurrentDuration )
                .orElse( Long.MAX_VALUE );

        long lCurrentDurationToCityNode = Optional
                .ofNullable( currentItineraryToCityNode )
                .map( ItineraryToCity::getCurrentDuration )
                .orElse( Long.MAX_VALUE );

        long lSegmentDuration = Optional
                .ofNullable( segment )
                .map( Segment::getDuration )
                .orElse( Long.MAX_VALUE );

        long lDurationToDestinationThroughThisNode = lCurrentDurationToCityNode + lSegmentDuration;

        /* Update itinerary by duration following Dijkstra algorithm. If new itinerary is shorter, change the old itinerary with this new one */
        if( lDurationToDestinationThroughThisNode <= lCurrentDurationToDestinationCity || lCurrentDurationToDestinationCity == Long.MAX_VALUE)
        {
            /* Get current itinerary */
            List<Segment> vNewItinerary = Optional
                    .ofNullable( currentItineraryToCityNode )
                    .map(ItineraryToCity::getSegmentsByDuration)
                    .map( ArrayList::new )
                    .orElseGet( ArrayList::new );

            /* Add this segment */
            Optional
                    .ofNullable( segment )
                    .ifPresent( vNewItinerary::add );

            /* Set the new itinerary */
            Optional
                    .ofNullable(currentItineraryToDestination)
                    .ifPresent(itineraryToCity -> itineraryToCity.setSegmentsByDuration( vNewItinerary ));
        }
    }

    /**
     * Apply dijkstra for each received itinerary segment for this city node. Uses number of connections as criteria
     * For mor info: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * @param sCityNode The city node
     * @param hItineraries Updated itineraries
     * @param segment Current itinerary segment
     */
    private void applyDijkstraToCityForSegmentInConnections( String sCityNode, HashMap<String, ItineraryToCity> hItineraries, Segment segment )
    {
        ItineraryToCity currentItineraryToCityNode = Optional
                .ofNullable( sCityNode )
                .map(hItineraries::get)
                .orElse(null);

        ItineraryToCity currentItineraryToDestination = Optional
                .ofNullable( segment )
                .map(Segment::getDestinationCity)
                .map(hItineraries::get)
                .orElse(null);

        int iCurrentConnectionsToDestinationCity = Optional
                .ofNullable( currentItineraryToDestination )
                .map( ItineraryToCity::getCurrentNumberOfConnections )
                .filter( iConnections -> iConnections != 0 )
                .orElse( Integer.MAX_VALUE );

        int iCurrentConnectionsToCityNode = Optional
                .ofNullable( currentItineraryToCityNode )
                .map( ItineraryToCity::getCurrentNumberOfConnections )
                .orElse( Integer.MAX_VALUE );

        int iConnectionsToDestinationThroughThisNode = iCurrentConnectionsToCityNode + 1;

        /* Update itinerary by duration following Dijkstra algorithm. If new itinerary is shorter, change the old itinerary with this new one */
        if( iConnectionsToDestinationThroughThisNode <= iCurrentConnectionsToDestinationCity )
        {
            List<List<Segment>> vNewItineraries = Optional
                    .ofNullable( currentItineraryToCityNode )
                    .map(ItineraryToCity::getSegmentsByConnections)
                    .map( ArrayList::new )
                    .orElseGet( ArrayList::new );

            vNewItineraries
                    .stream()
                    .sequential()
                    .forEach( itinerary -> itinerary.add( segment ) );

            if( vNewItineraries.size() == 0 )
            {
                List<Segment> newItinerary = new ArrayList<>();
                newItinerary.add( segment );
                vNewItineraries.add( newItinerary );
            }

            /* Equal number of connections, add new itinerary, if there are less number of connections, clean previous segments */
            if( iConnectionsToDestinationThroughThisNode < iCurrentConnectionsToDestinationCity && iCurrentConnectionsToDestinationCity != Integer.MAX_VALUE )
            {
                currentItineraryToDestination.clearSegmentsByConnections();
            }

            /* Add this new itinerary to this city node */
            Optional
                    .ofNullable( currentItineraryToDestination )
                    .ifPresent( itineraryToCity -> itineraryToCity.addSegmentsByConnections( vNewItineraries ) );
        }

    }

    /**
     * Checks if all nodes are processed
     * @param hItineraries Updated itineraries
     * @return TRUE if all of them are processed
     */
    private boolean areAllItinerariesProcessed( HashMap<String, ItineraryToCity> hItineraries )
    {
        return hItineraries
                .values()
                .stream()
                .sequential()
                .allMatch( ItineraryToCity::isCityProcessed );
    }

    /**
     * Gets the next city node to process in dijkstra algorithm
     * @param hItineraries Updated itineraries
     * @param sDestinationCity The destination city
     * @return The next city to be processed as node
     */
    private String getNextCityToProcess( HashMap<String, ItineraryToCity> hItineraries, String sDestinationCity )
    {
        /* Return one random non processed city node without taking into account the destination city.
        * Return the destination city when all nodes are processed  */
        return hItineraries
                .values()
                .stream()
                .sequential()
                .filter( city -> city.isCityProcessed() == false )
                .map( ItineraryToCity::getCity )
                .filter( city -> city.equals( sDestinationCity ) == false )
                .findFirst()
                .orElse( sDestinationCity );
    }

    /**
     * Creates the response wrapper
     * @param sOriginCity The origin city name
     * @param sDestinationCity The destination city name
     * @param hItineraries All itineraries
     * @return The result of the user request
     */
    private ItineraryResponse createItineraryResponse(
            String sOriginCity,
            String sDestinationCity,
            HashMap<String, ItineraryToCity> hItineraries )
    {
        ItineraryResponse itineraryResponse = new ItineraryResponse( sOriginCity, sDestinationCity );

        Optional
                .ofNullable(sDestinationCity)
                .map(hItineraries::get)
                .map(ItineraryToCity::getSegmentsByDuration)
                .ifPresent(itineraryResponse::setShortestInTime);

        Optional
                .ofNullable(sDestinationCity)
                .map( hItineraries::get )
                .map(ItineraryToCity::getSegmentsByConnections)
                .orElseGet(ArrayList::new)
                .stream()
                .sequential()
                .forEach( itineraryResponse::addShortestInConnections );

        return itineraryResponse;
    }


}
