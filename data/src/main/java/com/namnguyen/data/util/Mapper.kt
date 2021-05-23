package com.namnguyen.data.util

import com.namnguyen.data.model.GeocodeResponse
import com.namnguyen.data.model.WeatherResponse
import com.namnguyen.domain.model.GeoCodeModel
import com.namnguyen.domain.model.WeatherModel


fun WeatherResponse.WeatherForecast.toWeatherModel(): WeatherModel = WeatherModel(
    date = dt ?: 0L,
    averageTemp = temp?.run { min?.plus(max ?: 0.0)?.div(2.0) } ?: 0.0,
    pressure = pressure ?: 0,
    humidity = humidity ?: 0,
    desc = weather?.firstOrNull()?.description.orEmpty()
)

fun List<WeatherResponse.WeatherForecast?>.toWeatherModels(): List<WeatherModel> =
    this.map { it?.toWeatherModel() ?: WeatherModel(0L, 0.0, 0, 0, "") }

fun GeocodeResponse.Results.toGeoCodeModel(): GeoCodeModel = GeoCodeModel(
    lat = geometry?.lat,
    lng = geometry?.lng,
    cityName = components?.city
)

fun List<GeocodeResponse.Results?>.toGeoCodeModels(): List<GeoCodeModel> =
    this.map { it?.toGeoCodeModel() ?: GeoCodeModel( 0.0, 0.0, "") }