package com.alexis11hm.flightswidgetapp.ui.viewmodels

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis11hm.flightswidgetapp.core.Constants
import com.alexis11hm.flightswidgetapp.core.SwitchFlag
import com.alexis11hm.flightswidgetapp.domain.repository.WidgetConfigurationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WidgetConfigurationViewModel(
    private val widgetConfigurationRepository: WidgetConfigurationRepository
) : ViewModel() {

    private val _data : MutableStateFlow<Pair<SwitchFlag, Boolean>> = MutableStateFlow(Pair(SwitchFlag.DARK_THEME, false))
    val data = _data.asStateFlow()
    fun saveData(value : Boolean, switchFlag: SwitchFlag){
        val key = getKey(switchFlag)
        viewModelScope.launch {
            widgetConfigurationRepository.saveValue(value, key)
        }
    }

    fun getData(switchFlag : SwitchFlag){
        val key = getKey(switchFlag)
        viewModelScope.launch {
            widgetConfigurationRepository.getValue(key).collect { info ->
                _data.value = Pair(switchFlag, info)
            }
        }
    }

    private fun getKey(switchFlag : SwitchFlag) : Preferences.Key<Boolean> {
        return when(switchFlag){
            SwitchFlag.DARK_THEME -> Constants.DARK_THEME_KEY
            SwitchFlag.HIDE_DATE -> Constants.HIDE_TEXT_KEY
            SwitchFlag.SMALL_TEXT_SIZE -> Constants.SMALL_TEXT_SIZE_KEY
        }
    }

}