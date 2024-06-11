package com.alexis11hm.flightswidgetapp.domain.repository

import com.alexis11hm.flightswidgetapp.data.model.Flight
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    suspend fun getRandomFlight(): Flow<Flight>

}