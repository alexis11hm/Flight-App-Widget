package com.alexis11hm.flightswidgetapp.data.repository

import androidx.datastore.preferences.core.Preferences
import com.alexis11hm.flightswidgetapp.data.datasource.WidgetConfigurationDataSource
import com.alexis11hm.flightswidgetapp.domain.repository.WidgetConfigurationRepository
import kotlinx.coroutines.flow.Flow

class WidgetConfigurationRepositoryImpl(
    private val dataSource : WidgetConfigurationDataSource
) : WidgetConfigurationRepository {
    override suspend fun getValue(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataSource.getValue(key)
    }

    override suspend fun saveValue(value: Boolean, key: Preferences.Key<Boolean>) {
        return dataSource.saveValue(value, key)
    }
}