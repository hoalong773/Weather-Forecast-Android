package com.namnguyen.data.weather

import com.namnguyen.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherListService {
    @GET("/data/2.5/onecall")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("appid") appid: String,
        @Query("exclude") exclude: Int
    ): WeatherResponse
}