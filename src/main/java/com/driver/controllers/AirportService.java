package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.Date;

public class AirportService {

    AirportRepository ar = new AirportRepository();
    public void addAirport(Airport airport) {
        ar.addAirport(airport);
    }

    public String getLargestAirportName() {
        return ar.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return ar.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return ar.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return ar.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return ar.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return ar.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return ar.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String addFlight(Flight flight) {
        return ar.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return ar.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return ar.calculateRevenueOfAFlight(flightId);
    }

    public String addPassenger(Passenger passenger) {
        return ar.addPassenger(passenger);
    }
}
