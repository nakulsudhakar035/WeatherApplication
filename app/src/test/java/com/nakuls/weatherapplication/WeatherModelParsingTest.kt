package com.nakuls.weatherapplication

import com.google.gson.Gson
import com.nakuls.weatherapplication.data.model.Weather
import org.junit.Test
import org.junit.Assert.assertEquals

class WeatherModelParsingTest {

    private val gson = Gson()

    @Test
    fun parse_valid_json_into_models() {
        val json = """
        {
          "location": { "name": "Berlin" },
          "current": { 
            "temp_c": 20.0,
            "condition": { "text": "Sunny" }
          }
        }
        """.trimIndent()

        val weather = gson.fromJson(json, Weather::class.java)

        assertEquals("Berlin", weather.location.name)
        assertEquals(20.0, weather.current.temp_c.toDouble(), 0.0)
        assertEquals("Sunny", weather.current.condition.text)
    }
}