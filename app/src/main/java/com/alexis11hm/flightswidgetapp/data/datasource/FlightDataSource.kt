package com.alexis11hm.flightswidgetapp.data.datasource

import com.alexis11hm.flightswidgetapp.data.model.Flight

val fights = listOf(
    Flight(
        ticketNumber = "82942554788213",
        date = "Fri 5, February",
        travelTime = "1h 45m",
        cityFrom = "Los Angeles",
        cityFromAbbreviation = "LA",
        countryFrom = "United States",
        cityTo = "Guadalajara",
        cityToAbbreviation = "GDL",
        countryTo = "México",
        gate = "C8",
        flight = "FRW3211S",
        seat = "102",
        departureTime = "10:00",
        arrivalTime = "11:45",
        price = "$100"
    ),
    Flight(
        ticketNumber = "29239020101034",
        date = "Sat 22, January",
        travelTime = "7h 50m",
        cityFrom = "London",
        cityFromAbbreviation = "LDN",
        countryFrom = "United Kingdom",
        cityTo = "New York",
        cityToAbbreviation = "NY",
        countryTo = "United States",
        gate = "C2",
        flight = "TDS902Y",
        seat = "26",
        departureTime = "15:35",
        arrivalTime = "23:25",
        price = "$1200"
    ),
    Flight(
        ticketNumber = "93927446639271",
        date = "Thu 30, March",
        travelTime = "3h 5m",
        cityFrom = "Cancun",
        cityFromAbbreviation = "CAN",
        countryFrom = "México",
        cityTo = "Toronto",
        cityToAbbreviation = "TOR",
        countryTo = "Canada",
        gate = "D1",
        flight = "TWS019RT",
        seat = "10",
        departureTime = "16:00",
        arrivalTime = "19:05",
        price = "$400"
    ),
    Flight(
        ticketNumber = "93927446639271",
        date = "Mon 28, April",
        travelTime = "1h 50m",
        cityFrom = "Paris",
        cityFromAbbreviation = "PAR",
        countryFrom = "France",
        cityTo = "Munich",
        cityToAbbreviation = "MUN",
        countryTo = "Germany",
        gate = "A3",
        flight = "DRS244LP",
        seat = "45",
        departureTime = "00:00",
        arrivalTime = "01:50",
        price = "$200"
    ),
    Flight(
        ticketNumber = "93772372839238128",
        date = "Sun 15, September",
        travelTime = "8h 10m",
        cityFrom = "Tokyo",
        cityFromAbbreviation = "TOK",
        countryFrom = "Japan",
        cityTo = "Chicago",
        cityToAbbreviation = "CGO",
        countryTo = "United States",
        gate = "B4",
        flight = "YUP431XD",
        seat = "23",
        departureTime = "23:50",
        arrivalTime = "08:00",
        price = "$600"
    )
)

class FlightDataSource {

    fun getAllFlights(): List<Flight> = fights

}