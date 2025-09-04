package com.nakuls.weatherapplication

import com.nakuls.weatherapplication.data.model.Condition
import com.nakuls.weatherapplication.data.model.Current
import com.nakuls.weatherapplication.data.model.Location
import com.nakuls.weatherapplication.data.model.Weather
import com.nakuls.weatherapplication.data.remote.WeatherAPI
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeWeatherApi : WeatherAPI {

    // configure behavior from tests
    var shouldThrow: Boolean = false
    var nextWeather: Weather? = null
    var httpCode: Int = 200

    override suspend fun getWeather(
        apiKey: String,
        city: String
    ): Response<Weather> {
        if (shouldThrow) throw RuntimeException("network boom")
        val body = nextWeather ?: Weather(
            Current(
                cloud = "test",
                condition = Condition(
                    code = "test",
                    icon = "test",
                    text = "test"
                ),
                dewpoint_c = "test",
                dewpoint_f = "test",
                diff_rad = "test",
                dni = "test",
                feelslike_c = "test",
                feelslike_f = "test",
                gti = "test",
                gust_kph = "test",
                gust_mph = "test",
                heatindex_c = "test",
                heatindex_f = "test",
                humidity = "test",
                is_day = "test",
                last_updated = "test",
                last_updated_epoch = "test",
                precip_in = "test",
                precip_mm = "test",
                pressure_in = "test",
                pressure_mb = "test",
                short_rad = "test",
                temp_c = "test",
                temp_f = "test",
                uv = "test",
                vis_km = "test",
                vis_miles = "test",
                wind_degree = "test",
                wind_dir = "test",
                wind_kph = "test",
                wind_mph = "test",
                windchill_c = "test",
                windchill_f = "test"
            ),
            Location(
                country = "test",
                lat = "test",
                localtime = "test",
                localtime_epoch = "test",
                lon = "test",
                name = "test",
                region = "test",
                tz_id = "test"
            )
        )
        return if (httpCode in 200..299) {
            Response.success(body)
        } else {
            Response.error(httpCode, "".toResponseBody(null))
        }
    }
}