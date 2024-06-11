package com.alexis11hm.flightswidgetapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

class ViewModelFactory<T : ViewModel>(
    private val kClass : KClass<T>,
    val constructor : () -> T
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(kClass.java)) {
            return constructor() as T
        }
        throw IllegalArgumentException("Model class is not assignable from view model")
    }

}