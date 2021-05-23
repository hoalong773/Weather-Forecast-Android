package com.namnguyen.weather.splash

import androidx.lifecycle.lifecycleScope
import com.namnguyen.weather.MainActivity
import com.namnguyen.weather.R
import com.namnguyen.weather.base.BaseFragment
import com.namnguyen.weather.databinding.FragmentSplashBinding
import com.namnguyen.weather.ext.changeStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_TIME = 300L

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    R.layout.fragment_splash,
    SplashViewModel::class
) {

    private val mMainActivity: MainActivity by lazy { activity as MainActivity }

    private val navDispatcher get() = mMainActivity.navDispatcher

    override fun FragmentSplashBinding.initViews() {
        activity.changeStatusBar(
            fullLayout = true,
            color = getColor(R.color.colorPrimary)
        )
        mViewModel.updateState(SplashState.HomeActivity)
        observableData()
    }

    private fun observableData() {
        mViewModel.splashState.observe(viewLifecycleOwner) {
            val splashState = it ?: return@observe
            if (splashState is SplashState.HomeActivity) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() = lifecycleScope.launch {
        delay(DELAY_TIME)
        navDispatcher.navigateToHome()
    }

}
