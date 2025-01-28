package com.example.weather_compose.data

import kotlinx.serialization.Serializable

@Serializable
data class Astro(
    val is_moon_up: String,
    val is_sun_up: String,
    val moon_illumination: String,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)