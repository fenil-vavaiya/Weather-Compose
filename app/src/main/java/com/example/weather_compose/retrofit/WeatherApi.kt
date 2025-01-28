package com.example.weather_compose.retrofit

import com.example.weather_compose.data.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query("key") apikey : String,
        @Query("q") city : String,
        @Query("days") days : String,
    ) : Response<WeatherModel>

}