package com.alexis11hm.flightswidgetapp.ui.screens

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexis11hm.flightswidgetapp.core.FlightUiState
import com.alexis11hm.flightswidgetapp.data.model.Flight
import com.alexis11hm.flightswidgetapp.databinding.ActivityHomeBinding
import com.alexis11hm.flightswidgetapp.ui.registry.FlightRegistry
import com.alexis11hm.flightswidgetapp.ui.viewmodels.FlightViewModel
import com.alexis11hm.flightswidgetapp.ui.widgets.FlightWidget
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
        if (isVersionEqualsOrGreaterThanOreo()){
            showPinShortcut()
        }
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

    private fun isVersionEqualsOrGreaterThanOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPinShortcut(){
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val myProvider = ComponentName(this, FlightWidget::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            val successCallback = PendingIntent.getBroadcast(
                this,
                0,
                Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            appWidgetManager.requestPinAppWidget(myProvider, null, successCallback)
        }
    }
}