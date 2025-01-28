package com.example.weather_compose.ktor

import com.example.weather_compose.data.WeatherModel
import com.example.weather_compose.retrofit.RetrofitInstance
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

object KtorEndPoints {

    suspend fun getWeatherData(key: String, city: String): WeatherModel =
        KtorClient.httpClient.get  {
            url("${RetrofitInstance.BASE_URL}v1/forecast.json")
            parameter("key", key)
            parameter("q", city)
            parameter("days", 7)
        }
}