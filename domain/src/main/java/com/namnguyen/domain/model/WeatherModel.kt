package com.namnguyen.domain.model

class WeatherModel(
    val date: Long,
    val averageTemp: Double,
    val pressure: Int,
    val humidity: Int,
    val desc: String
)