package com.nakuls.weatherapplication.domain

import com.nakuls.weatherapplication.data.model.Weather
import retrofit2.Response

interface WeatherRepository {
     suspend fun fetchWeatherData(
         apiKey: String,
         city: String
     ): Response<Weather>
}