package com.nakuls.weatherapplication.api

import androidx.compose.ui.input.key.Key
import com.nakuls.weatherapplication.viewModel.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Response<Weather>

}