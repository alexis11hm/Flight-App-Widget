package com.alexis11hm.flightswidgetapp.data.repository

import com.alexis11hm.flightswidgetapp.data.datasource.FlightDataSource
import com.alexis11hm.flightswidgetapp.data.model.Flight
import com.alexis11hm.flightswidgetapp.domain.repository.FlightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlightRepositoryImpl(private val dataSource: FlightDataSource) : FlightRepository {

    override suspend fun getRandomFlight(): Flow<Flight> {
        return flow {
            emit(dataSource.getAllFlights().random())
        }
    }

}