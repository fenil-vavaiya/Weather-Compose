package com.example.weather_compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_compose.data.WeatherModel
import com.example.weather_compose.ktor.KtorEndPoints
import com.example.weather_compose.response.ErrorType
import com.example.weather_compose.response.NetworkResponse
import com.example.weather_compose.util.Const
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class WeatherViewModel : ViewModel() {

    private val _weatherResult =
        MutableStateFlow<NetworkResponse<WeatherModel>>(NetworkResponse.DoNone)
    val weatherResult: StateFlow<NetworkResponse<WeatherModel>> = _weatherResult.asStateFlow()

    /*private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult*/


    fun getData(city: String) {
        viewModelScope.launch {
            _weatherResult.value = NetworkResponse.Loading
            try {
                val response = KtorEndPoints.getWeatherData(Const.KEY, city)
                _weatherResult.value = NetworkResponse.Success(response)
            } catch (e: Exception) {
                val errorType = when (e) {
                    is UnknownHostException -> ErrorType.NETWORK
                    is SocketTimeoutException -> ErrorType.TIMEOUT
                    else -> ErrorType.UNKNOWN
                }
                _weatherResult.value =
                    NetworkResponse.Error(errorType, e.message ?: "Unknown error occurred")
            }

        }
    }
}