package com.nakuls.weatherapplication.viewModel

import com.nakuls.weatherapplication.data.model.Weather

data class WeatherUIState(
    val city: String = "",
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""

)