package com.example.weather_compose.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)