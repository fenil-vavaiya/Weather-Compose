package com.example.weather_compose.data

import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    val code: String,
    val icon: String,
    val text: String
)