package com.alexis11hm.flightswidgetapp.ui.registry

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.alexis11hm.flightswidgetapp.core.Constants
import com.alexis11hm.flightswidgetapp.core.ViewModelFactory
import com.alexis11hm.flightswidgetapp.data.datasource.WidgetConfigurationDataSource
import com.alexis11hm.flightswidgetapp.data.repository.WidgetConfigurationRepositoryImpl
import com.alexis11hm.flightswidgetapp.ui.viewmodels.WidgetConfigurationViewModel

class WidgetConfigurationRegistry(
    context: Context
){

    companion object {

        val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

    }

    private val dataSource by lazy {
        WidgetConfigurationDataSource(context.dataStore)
    }

    private val repository by lazy {
        WidgetConfigurationRepositoryImpl(dataSource)
    }

    fun provide() : ViewModelFactory<WidgetConfigurationViewModel> {
        return ViewModelFactory(WidgetConfigurationViewModel::class){
            WidgetConfigurationViewModel(repository)
        }
    }

}