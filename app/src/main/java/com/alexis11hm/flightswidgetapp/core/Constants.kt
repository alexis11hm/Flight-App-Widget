package com.alexis11hm.flightswidgetapp.core

import androidx.datastore.preferences.core.booleanPreferencesKey

class Constants {

    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey(SwitchFlag.DARK_THEME.name)
        val HIDE_TEXT_KEY = booleanPreferencesKey(SwitchFlag.HIDE_DATE.name)
        val SMALL_TEXT_SIZE_KEY = booleanPreferencesKey(SwitchFlag.SMALL_TEXT_SIZE.name)
        const val DATA_STORE_NAME = "settings"
    }

}