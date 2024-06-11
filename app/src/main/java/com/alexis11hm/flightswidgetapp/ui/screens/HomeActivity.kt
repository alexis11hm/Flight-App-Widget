package com.alexis11hm.flightswidgetapp.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexis11hm.flightswidgetapp.core.FlightUiState
import com.alexis11hm.flightswidgetapp.data.model.Flight
import com.alexis11hm.flightswidgetapp.databinding.ActivityHomeBinding
import com.alexis11hm.flightswidgetapp.ui.registry.FlightRegistry
import com.alexis11hm.flightswidgetapp.ui.viewmodels.FlightViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    private val flightViewModel by viewModels<FlightViewModel> {
        FlightRegistry().provide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init(){
        getData()
    }

    private fun getData(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flightViewModel.uiState.collect { uiState ->
                    when(uiState){
                        is FlightUiState.Success -> {
                            setDataToUI(uiState.flight)
                        }
                        else -> {
                            Log.d("FlightUiState", "Something was wrong")
                        }
                    }
                }
            }
        }
    }

    private fun setDataToUI(flight: Flight){
        with(flight){
            binding.ticketNumberRealText.text = ticketNumber
            binding.dateText.text = date
            binding.fromText.text = cityFrom
            binding.fromCountryText.text = countryFrom
            binding.toText.text = cityTo
            binding.toCountryText.text = countryTo
            binding.gateValueText.text = gate
            binding.flightValueText.text = flight.flight
            binding.seatValueText.text = seat
            binding.departureDateText.text = departureTime
            binding.arrivalDateText.text = arrivalTime
            binding.priceText.text = price
        }
    }
}