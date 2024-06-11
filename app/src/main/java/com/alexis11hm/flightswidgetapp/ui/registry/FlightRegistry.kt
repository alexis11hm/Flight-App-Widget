package com.alexis11hm.flightswidgetapp.ui.registry

import com.alexis11hm.flightswidgetapp.core.ViewModelFactory
import com.alexis11hm.flightswidgetapp.data.datasource.FlightDataSource
import com.alexis11hm.flightswidgetapp.data.repository.FlightRepositoryImpl
import com.alexis11hm.flightswidgetapp.ui.viewmodels.FlightViewModel

class FlightRegistry {

    private val flightDataSource by lazy {
        FlightDataSource()
    }

    private val flightRepository by lazy {
        FlightRepositoryImpl(flightDataSource)
    }

    fun provide() : ViewModelFactory<FlightViewModel> {
        return ViewModelFactory(FlightViewModel::class) {
            FlightViewModel(flightRepository)
        }
    }

}