package com.namnguyen.weather.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.namnguyen.weather.navigation.NavigationDispatcher

abstract class BaseActivity : AppCompatActivity() {
    abstract val viewModel: BaseViewModel
    abstract val navController: NavController
    abstract val navDispatcher: NavigationDispatcher
}
