package com.namnguyen.weather.di.modules

import androidx.lifecycle.ViewModel
import com.namnguyen.weather.di.mapkeys.ViewModelKey
import com.namnguyen.weather.home.HomeViewModel
import com.namnguyen.weather.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@[InstallIn(SingletonComponent::class) Module]
interface ViewModelModule {
    @get:[Binds IntoMap ViewModelKey(HomeViewModel::class)]
    val HomeViewModel.homeViewModel: ViewModel

    @get:[Binds IntoMap ViewModelKey(SplashViewModel::class)]
    val SplashViewModel.splashViewModel: ViewModel
}