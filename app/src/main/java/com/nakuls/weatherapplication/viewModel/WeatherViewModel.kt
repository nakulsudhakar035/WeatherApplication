package com.nakuls.weatherapplication.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.weatherapplication.api.Constants
import com.nakuls.weatherapplication.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()
    private val weatherAPI = RetrofitInstance.weatherAPI

    fun getWeatherforCity(city: String){
        viewModelScope.launch {
            val response = weatherAPI.getWeather(Constants.apikey, city)
            if(response.isSuccessful){
                Log.i("Weather", response.body().toString())
            } else {
                Log.i("Weather",response.toString())
            }
        }

    }

    fun onCityChanged(newCity: String) {
        _uiState.update { currentState ->
            currentState.copy(
                city = newCity
            )
        }
    }
}