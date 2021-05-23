package com.namnguyen.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.namnguyen.weather.base.BaseActivity
import com.namnguyen.weather.base.BaseViewModel
import com.namnguyen.weather.databinding.ActivityMainBinding
import com.namnguyen.weather.ext.SingleLiveData
import com.namnguyen.weather.ext.connectivity.ConnectivityProvider
import com.namnguyen.weather.ext.connectivity.ConnectivityStateListener
import com.namnguyen.weather.ext.connectivity.NetworkState
import com.namnguyen.weather.navigation.NavigationDispatcherImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val navController by lazy { findNavController(R.id.navHostFragment) }

    override val navDispatcher by lazy { NavigationDispatcherImpl(navController) }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by viewModels { factory }
    override val viewModel: BaseViewModel get() = mViewModel

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}