package com.nakuls.weatherapplication

import com.nakuls.weatherapplication.data.model.Condition
import com.nakuls.weatherapplication.data.model.Current
import com.nakuls.weatherapplication.data.model.Location
import com.nakuls.weatherapplication.data.model.Weather
import com.nakuls.weatherapplication.viewModel.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun getWeather_success_updates_state() = runTest {
        val fakeApi = FakeWeatherApi().apply {
            nextWeather = Weather(
                location = Location(
                    name = "Berlin",
                    country = "test",
                    lat = "test",
                    localtime = "test",
                    localtime_epoch = "test",
                    lon = "test",
                    region = "test",
                    tz_id = "test"
                ),
                current = Current(
                    temp_c = "test",
                    condition = Condition(
                        code = "test",
                        icon = "test",
                        text = "test"
                    ),
                    cloud = "test",
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
                )
            )
        }
        val vm = WeatherViewModel(fakeApi)
        vm.getWeatherForCity("Berlin")

        advanceUntilIdle()

        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.weather)
        assertEquals("Berlin", state.weather?.location?.name)
    }

    @Test
    fun getWeather_failure_sets_error() = runTest {
        val fakeApi = FakeWeatherApi().apply {
            httpCode = 404
        }
        val vm = WeatherViewModel(fakeApi)

        vm.getWeatherForCity("Nowhere")
        advanceUntilIdle()

        val state = vm.uiState.value
        assertFalse(state.isLoading)
        assertEquals(null, state.weather)
    }
}