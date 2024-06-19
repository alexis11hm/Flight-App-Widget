package com.alexis11hm.flightswidgetapp.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface WidgetConfigurationRepository {

    suspend fun getValue(key : Preferences.Key<Boolean>) : Flow<Boolean>
    suspend fun saveValue(value : Boolean, key : Preferences.Key<Boolean>)

}