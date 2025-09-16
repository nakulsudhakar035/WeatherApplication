package com.nakuls.weatherapplication.data.repository

import com.nakuls.weatherapplication.data.model.Weather
import com.nakuls.weatherapplication.data.remote.WeatherAPI
import com.nakuls.weatherapplication.domain.WeatherRepository
import com.nakuls.weatherapplication.viewModel.WeatherViewModel
import retrofit2.Response

class WeatherRepositoryImpl(
    val weatherAPI: WeatherAPI
): WeatherRepository {

    override suspend fun fetchWeatherData(
        apiKey: String,
        city: String
    ): Response<Weather> {
        return weatherAPI.getWeather(
            apiKey,
            city
        )
    }


}