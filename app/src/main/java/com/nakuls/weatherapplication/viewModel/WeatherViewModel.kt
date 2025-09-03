package com.nakuls.weatherapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.weatherapplication.utils.Constants
import com.nakuls.weatherapplication.data.remote.RetrofitInstance
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
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val response = weatherAPI.getWeather(Constants.apikey, city)
                if (response.isSuccessful) {
                    response.body()?.let { weatherObj ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                weather = weatherObj
                            )
                        }
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessage = "Unable to fetch information currently"
                        )
                    }
                }
            }  catch (exception: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        errorMessage = "Unable to fetch information currently"
                    )
                }
            } finally {
                _uiState.update { currentState ->
                    currentState.copy(
                        city = "",
                        isLoading = false
                    )
                }
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