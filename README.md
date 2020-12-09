# Adidas challenge

The aim of this challenge is to create an application that given an origin city and a destination city in a determined scenario, it returns the shortest itinerary to reach the destination in terms of time, and another set of itineraries with less number of interconnections.

This application is divided in two services:
- Segment provider ( project in "adidas" folder ): This service connects to a PostgreSQL database where the scenario is stored, and provides via REST services those segments required in terms of origin city. This service is accesible with a web browser using the url http://<IP_ADDRESS>:8080 and simple form asks you to introduce a city to obtain the segments. This segments can be consulted also by the complete URL http://<IP_ADDRESS>:8080/city?cityOrigin=<CITY>, URL used by the other service.
- Itinerary calculator: This is another REST service that asks user for two city names of the loaded scenario via URL http://<IP_ADDRESS>:8081/ in a web browser. User must write the name of both cities and press click button. The result is a JSON formatted text with the shortest itinerary in time and a set of the shortest itineraries in connections
  
Both services can be run as java -jar <Service name>.jar, uploaded in this main folder.
  
The scenario of all cities can be seen in the file cities.jpg. A postgreSQL script that generates this scenario is added to this project. You must execute the cities.sql into a standard postgreSQL database. This services use postgreSQL credentials postgres/admin.
This scenario contains the following cities: Zaragoza, Huesca, Teruel, Madrid, Barcelona and Cádiz.
Departure and arrival time for each city is completely random, except the segment Zaragoza - Madrid, which has a high value in order to force algorithm to go through other cities.

This problem and scenario is modeled as a traffic routing optimization problem, so to solve all these optimal routes the service uses Dijkstra's algorithm.
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
In the Itinerary provider code you can find the detailed implementation of this Dijkstra algorithm in each method.

To build the app and create the JAR files I have used maven. I only had to add to the Segment provider service the dependency with the postgreSQL database connector.
- mvn package
