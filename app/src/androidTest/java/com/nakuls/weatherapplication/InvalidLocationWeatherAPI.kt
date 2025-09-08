package com.nakuls.weatherapplication

import com.nakuls.weatherapplication.data.model.Weather
import com.nakuls.weatherapplication.data.remote.WeatherAPI
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class InvalidLocationWeatherAPI: WeatherAPI {
    override suspend fun getWeather(
        apiKey: String,
        city: String
    ): Response<Weather> {
        delay(200)
        val errorResponseBody = createErrorResponseBody(
            """{"error": {"code": 400, "message": "No matching location found"}}"""
        )

        return Response.error(404, errorResponseBody)
    }

    private fun createErrorResponseBody(json: String): ResponseBody {
        return json.toResponseBody("application/json".toMediaType())
    }
}