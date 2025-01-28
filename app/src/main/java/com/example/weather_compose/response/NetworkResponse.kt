package com.example.weather_compose.response

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()

    data class Error(val errorType: ErrorType, val message: String) : NetworkResponse<Nothing>()

      data object Loading : NetworkResponse<Nothing>()
      data object DoNone : NetworkResponse<Nothing>()
}