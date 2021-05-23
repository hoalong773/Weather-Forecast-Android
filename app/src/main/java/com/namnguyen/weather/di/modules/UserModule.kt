package com.namnguyen.weather.di.modules

import com.namnguyen.data.geocode.GeoCodeRepositoryImpl
import com.namnguyen.data.weather.WeatherRepositoryImpl
import com.namnguyen.domain.city.GeoCodeRepository
import com.namnguyen.domain.city.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[InstallIn(SingletonComponent::class) Module]
abstract class UserModule {

    // repository
    @Binds
    abstract fun WeatherRepositoryImpl.bindWeatherRepository(): WeatherRepository

    // Geocode repository
    @Binds
    abstract fun GeoCodeRepositoryImpl.bindGeocodeRepository(): GeoCodeRepository
}