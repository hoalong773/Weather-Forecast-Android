package com.namnguyen.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("daily")
    val listWeatherForecast: List<WeatherForecast?>?,
) {
    data class WeatherForecast(
        @SerializedName("dt")
        val dt: Long?,
        @SerializedName("humidity")
        val humidity: Int?,
        @SerializedName("pressure")
        val pressure: Int?,
        @SerializedName("temp")
        val temp: Temp?,
        @SerializedName("weather")
        val weather: List<Weather?>?
    ) {
        data class Temp(
            @SerializedName("day")
            val day: Double?,
            @SerializedName("eve")
            val eve: Double?,
            @SerializedName("max")
            val max: Double?,
            @SerializedName("min")
            val min: Double?,
            @SerializedName("morn")
            val morn: Double?,
            @SerializedName("night")
            val night: Double?
        )

        data class Weather(
            @SerializedName("description")
            val description: String?,
            @SerializedName("icon")
            val icon: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("main")
            val main: String?
        )
    }
}