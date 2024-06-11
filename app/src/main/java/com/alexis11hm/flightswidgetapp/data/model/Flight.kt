package com.alexis11hm.flightswidgetapp.data.model

data class Flight(
    val ticketNumber : String = "",
    val date : String = "",
    val travelTime : String = "",
    val cityFrom : String = "",
    val cityFromAbbreviation : String = "",
    val countryFrom :  String = "",
    val cityTo : String = "",
    val cityToAbbreviation : String = "",
    val countryTo : String = "",
    val gate : String = "",
    val flight : String = "",
    val seat : String = "",
    val departureTime : String = "",
    val arrivalTime : String = "",
    val price : String = ""
)
