package com.alexis11hm.flightswidgetapp.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.alexis11hm.flightswidgetapp.R
import com.alexis11hm.flightswidgetapp.data.datasource.FlightDataSource
import com.alexis11hm.flightswidgetapp.data.model.Flight
import com.alexis11hm.flightswidgetapp.data.repository.FlightRepositoryImpl
import com.alexis11hm.flightswidgetapp.domain.repository.FlightRepository
import com.alexis11hm.flightswidgetapp.ui.screens.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightWidget : AppWidgetProvider() {

    private lateinit var dataSource: FlightDataSource
    private lateinit var repository: FlightRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        dataSource = FlightDataSource()
        repository = FlightRepositoryImpl(dataSource)

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, repository)
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    repository: FlightRepository
) {

    val views = RemoteViews(context.packageName, R.layout.flight_widget)
    views.setOnClickPendingIntent(R.id.main_widget_layout, goToApp(context))

    val coroutine = CoroutineScope(Dispatchers.IO)
    coroutine.launch {
        repository.getRandomFlight().collect { flight ->
            withContext(Dispatchers.Main) {
                delay(5000)
                setupDataToViews(context, appWidgetManager, appWidgetId, views, flight)
            }
        }
    }
}

private fun setupDataToViews(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    views: RemoteViews,
    flight: Flight
) {
    with(views) {
        val departure = context.getString(R.string.departure_widget, flight.departureTime)
        val arrival = context.getString(R.string.arrival_widget, flight.arrivalTime)
        val travelTime = context.getString(R.string.directo_widget, flight.travelTime)
        setTextViewText(R.id.departure_time_widget, departure)
        setTextViewText(R.id.arrival_time_widget, arrival)
        setTextViewText(R.id.city_from_abbreviation_widget, flight.cityFromAbbreviation)
        setTextViewText(R.id.city_to_abbreviation_widget, flight.cityToAbbreviation)
        setTextViewText(R.id.city_from_widget, flight.cityFrom)
        setTextViewText(R.id.city_to_widget, flight.cityTo)
        setTextViewText(R.id.travel_time_widget, travelTime)
        setTextViewText(R.id.departure_time_footer_widget, flight.departureTime)
        setTextViewText(R.id.city_from_abbreviation_footer_widget, flight.cityFromAbbreviation)
        setTextViewText(R.id.departure_time_footer_widget, flight.departureTime)
        setTextViewText(R.id.seat_widget, flight.seat)
        appWidgetManager.updateAppWidget(appWidgetId, this)
        updateWidget(context)
    }
}

private fun updateWidget(context: Context) {
    val applicationContext = context.applicationContext
    val intent = Intent(context, FlightWidget::class.java).apply {
        action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
    }
    val ids: IntArray = AppWidgetManager.getInstance(applicationContext)
        .getAppWidgetIds(ComponentName(applicationContext, FlightWidget::class.java))
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    context.sendBroadcast(intent)
}

private fun goToApp(context: Context): PendingIntent {
    return PendingIntent.getActivity(
        context,
        0,
        Intent(context, HomeActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}
