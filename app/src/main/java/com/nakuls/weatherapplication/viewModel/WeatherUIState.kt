package com.nakuls.weatherapplication.viewModel

import android.health.connect.datatypes.units.Temperature

data class WeatherUIState(
    val city: String = "",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""

)