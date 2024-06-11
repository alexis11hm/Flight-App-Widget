package com.alexis11hm.flightswidgetapp.core

import com.alexis11hm.flightswidgetapp.data.model.Flight

sealed class FlightUiState {

    data object Loading : FlightUiState()
    data class Success(val flight: Flight) : FlightUiState()
    data class Error(val error: Throwable) : FlightUiState()

}