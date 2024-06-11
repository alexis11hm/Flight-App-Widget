package com.alexis11hm.flightswidgetapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis11hm.flightswidgetapp.core.FlightUiState
import com.alexis11hm.flightswidgetapp.domain.repository.FlightRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlightViewModel(
    private val flightRepository: FlightRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlightUiState>(FlightUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getRandomFlight()
    }

    private fun getRandomFlight() {
        viewModelScope.launch {
            while (true){
                flightRepository.getRandomFlight()
                    .collect { flight ->
                        _uiState.value = FlightUiState.Success(flight)
                    }
                delay(5000)
            }
        }
    }
}