package com.namnguyen.weather.di.modules

import com.namnguyen.data.geocode.GeocodeService
import com.namnguyen.data.net.ApiConnection
import com.namnguyen.data.util.provideGeoService
import com.namnguyen.data.util.provideService
import com.namnguyen.data.weather.WeatherListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[InstallIn(SingletonComponent::class) Module]
object AppModule {

    @[Provides Singleton]
    internal fun provideWeatherListService(apiConnection: ApiConnection): WeatherListService {
        return apiConnection.provideService()
    }

    @[Provides Singleton]
    internal fun provideGeocodeService(apiConnection: ApiConnection): GeocodeService {
        return apiConnection.provideGeoService()
    }
}