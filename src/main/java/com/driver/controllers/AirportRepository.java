package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class AirportRepository {

    HashMap<String,Airport> portdb;

    HashMap<Integer,Passenger> passengers;
    HashMap<Integer,Flight> flights;
    HashMap<Integer, HashSet<Integer>> filledflights;
    HashMap<Integer,HashSet<Integer>> pass_flight;//passenger in flightid

    public AirportRepository(){
        portdb = new HashMap<>();
        passengers = new HashMap<>();
        flights = new HashMap<>();
        filledflights = new HashMap<>();
        pass_flight = new HashMap<>();
    }

    public void addAirport(Airport airport) {
        portdb.put(airport.getAirportName(),airport);
    }

    public String getLargestAirportName() {
        String largest = null;
        int max = -1;
        for(String airport: portdb.keySet()){
            if(max<portdb.get(airport).getNoOfTerminals()){
                max = portdb.get(airport).getNoOfTerminals();
                largest = portdb.get(airport).getAirportName();
            }
            else if(max==portdb.get(airport).getNoOfTerminals()){
                if(largest.compareTo(portdb.get(airport).getAirportName())>0)
                    largest = portdb.get(airport).getAirportName();
            }
        }
        return largest;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double min = -1;
        for(int flightid:flights.keySet()){
            Flight flight = flights.get(flightid);
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity))
                if(min == -1 || min>flight.getDuration())
                    min = flight.getDuration();
        }
        return min;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        if(!portdb.containsKey(airportName))
            return 0;
        int total = 0;
        City city = portdb.get(airportName).getCity();
        for(Flight flight:flights.values()){
            if(!date.equals(flight.getFlightDate()))
                continue;

            if(flight.getFromCity().equals(city) || flight.getToCity().equals(city)){
                if(filledflights.containsKey(flight.getFlightId()))
                    total += filledflights.get(flight.getFlightId()).size();
            }
        }

        return total;
    }

    public int calculateFlightFare(Integer flightId) {
        int fare = 0;
        if(filledflights.containsKey(flightId))
            return 3000 + filledflights.get(flightId).size() * 50;
        else
            return 0;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        Flight flight = flights.getOrDefault(flightId,null);
        HashSet<Integer> curr = filledflights.getOrDefault(flightId,new HashSet<>());
        if(flight==null || curr.contains(passengerId) || curr.size()>=flight.getMaxCapacity())
            return "FAILURE";
        curr.add(passengerId);
        filledflights.put(flightId,curr);
        HashSet<Integer> passflight = pass_flight.getOrDefault(passengerId,new HashSet<>());
        passflight.add(flightId);
        pass_flight.put(passengerId,passflight);
        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(!flights.containsKey(flightId) || !passengers.containsKey(passengerId) || !filledflights.get(flightId).contains(flightId))
            return "FAILURE";
        filledflights.get(flightId).remove(passengerId);
        pass_flight.get(passengerId).remove(flightId);
        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        if(!pass_flight.containsKey(passengerId))
            return 0;
        return pass_flight.get(passengerId).size();
    }

    public String addFlight(Flight flight) {
        flights.put(flight.getFlightId(),flight);
        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(!flights.containsKey(flightId))
            return null;
        City city = flights.get(flightId).getFromCity();

        for(String airport:portdb.keySet()){
            if(city.equals(portdb.get(airport).getCity()))
                return airport;
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return calculateFlightFare(flightId);
    }

    public String addPassenger(Passenger passenger) {
        passengers.put(passenger.getPassengerId(),passenger);
        return "SUCCESS";
    }
}
