package com.namnguyen.weather.util

import com.namnguyen.domain.model.WeatherModel
import com.namnguyen.weather.model.WeatherInfo

fun WeatherModel.toWeatherInfo(): WeatherInfo =
    WeatherInfo(date, averageTemp, pressure, humidity, desc)

fun List<WeatherModel>.toWeatherInfoList(): List<WeatherInfo> = map { it.toWeatherInfo() }