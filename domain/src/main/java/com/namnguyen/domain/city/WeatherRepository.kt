package com.namnguyen.domain.city

import com.namnguyen.domain.base.BaseResult
import com.namnguyen.domain.model.GeoCodeModel
import com.namnguyen.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getListWeather(
        lat: Double,
        lng: Double,
        appId: String,
        exclude: Int = 7 // just get daily
    ): Flow<BaseResult<List<WeatherModel>>>
}

interface GeoCodeRepository {
    fun getLocationByName(
        cityName: String,
        key: String
    ): Flow<BaseResult<GeoCodeModel>>
}