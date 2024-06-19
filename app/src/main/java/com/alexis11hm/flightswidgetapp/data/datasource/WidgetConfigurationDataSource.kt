package com.alexis11hm.flightswidgetapp.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WidgetConfigurationDataSource(
    private val dataStore: DataStore<Preferences>
) {

    fun getValue(key : Preferences.Key<Boolean>) : Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    suspend fun saveValue(value : Boolean, key : Preferences.Key<Boolean>){
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

}