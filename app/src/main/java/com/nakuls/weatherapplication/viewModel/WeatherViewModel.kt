package com.nakuls.weatherapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakuls.weatherapplication.utils.Constants
import com.nakuls.weatherapplication.data.remote.RetrofitInstance
import com.nakuls.weatherapplication.data.remote.WeatherAPI
import com.nakuls.weatherapplication.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    fun getWeatherForCity(city: String){
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val response = weatherRepository.fetchWeatherData(Constants.apikey, city)
                if (response.isSuccessful) {
                    response.body()?.let { weatherObj ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                weather = weatherObj,
                                errorMessage = ""
                            )
                        }
                    }
                } else if (response.errorBody() != null){
                    val errorMessage = try {
                        val jsonString = response.errorBody()!!.string()
                        val jsonObject = JSONObject(jsonString)
                        val errorObject = jsonObject.getJSONObject("error")
                        errorObject.getString("message")
                    } catch (e: Exception) {
                        "Unable to fetch information currently"
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessage = errorMessage
                        )
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