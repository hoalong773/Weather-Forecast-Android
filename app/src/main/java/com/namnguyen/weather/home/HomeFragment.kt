package com.namnguyen.weather.home

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.SimpleItemAnimator
import com.namnguyen.data.BuildConfig
import com.namnguyen.domain.base.BaseResult
import com.namnguyen.weather.R
import com.namnguyen.weather.base.BaseFragment
import com.namnguyen.weather.common.SimpleDialog
import com.namnguyen.weather.databinding.FragmentHomeBinding
import com.namnguyen.weather.ext.changeStatusBar
import com.namnguyen.weather.ext.connectivity.ConnectivityProvider
import com.namnguyen.weather.ext.connectivity.ConnectivityStateListener
import com.namnguyen.weather.ext.connectivity.NetworkState
import com.namnguyen.weather.ext.hideSoftKeyboard
import com.namnguyen.weather.ext.showSoftKeyboard
import com.namnguyen.weather.home.adapter.WeatherHistoryListAdapter
import com.namnguyen.weather.home.adapter.WeatherItemDecorator
import com.namnguyen.weather.home.adapter.WeatherListAdapter
import com.namnguyen.weather.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home,
    HomeViewModel::class
), ConnectivityStateListener {

    private lateinit var adapter: WeatherListAdapter
    private lateinit var adapterHistory: WeatherHistoryListAdapter
    private lateinit var textWatcher: TextWatcher
    private var simpleDialog: SimpleDialog? = null

    private val connectivityProvider by lazy { ConnectivityProvider.createProvider(requireContext()) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun FragmentHomeBinding.initViews() {
        viewModel ?: run {
            viewModel = mViewModel
            initUI()
            initEvents()
            activity.changeStatusBar(
                fullLayout = false,
                lightBackground = true,
                color = getColor(R.color.white)
            )
        }
        observable()
    }

    override fun onResume() {
        super.onResume()
        mBinding.edtWeather.apply {
            addTextChangedListener(textWatcher)
            requestFocus()
            showSoftKeyboard()
        }
    }

    override fun onPause() {
        mBinding.edtWeather.apply {
            removeTextChangedListener(textWatcher)
            clearFocus()
            hideSoftKeyboard()
        }
        super.onPause()
    }

    override fun onDestroy() {
        simpleDialog?.dismiss()
        super.onDestroy()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun observable() = mViewModel.run {

        listWeatherLiveData.observe(viewLifecycleOwner) { weatherModels ->
            if (weatherModels.isNullOrEmpty()) return@observe
            adapter.submitList(weatherModels.toWeatherInfoList())
            updateEmptyView(isHideEmptyView = true, mBinding.edtWeather.text.isNullOrBlank())
        }

        mListHistoryCityLive.observe(viewLifecycleOwner) {
            adapterHistory.submitList(it)
            updateEmptyView(isHideEmptyView = true, mBinding.edtWeather.text.isNullOrBlank())
        }

        geoCodeResultLive.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResult.Loading -> {
                    showProgress()
                }
                is BaseResult.Success -> {
                    it.data.let { geocodeModel ->
                        mViewModel.run {
                            val list = mListHistoryCityLive.value ?: arrayListOf()
                            val text = mBinding.edtWeather.text.toString()
                            if (list.contains(text).not()) {
                                list.add(text)
                                setHistoryCity(list)
                            }
                            getWeatherForeCast(
                                geocodeModel.lat ?: 0.0,
                                geocodeModel.lng ?: 0.0
                            )
                        }
                    }
                }
                is BaseResult.Failed -> {
                    hideProgress()
                    handleCheckInFailed(
                        it.status, it.exception
                    )
                    adapter.submitList(emptyList())
                    updateEmptyView(
                        isHideEmptyView = false,
                        mBinding.edtWeather.text.isNullOrBlank()
                    )
                }
                is BaseResult.Error -> {
                    hideProgress()
                    handleCommonError(it.error)
                    adapter.submitList(emptyList())
                    updateEmptyView(
                        isHideEmptyView = false,
                        mBinding.edtWeather.text.isNullOrBlank()
                    )
                }
                else -> {
                }
            }
        }

        isFailMessageLive.observe(viewLifecycleOwner) {
            it ?: return@observe
            handleCheckInFailed(
                it.first, it.second
            )
            adapter.submitList(emptyList())
            updateEmptyView(isHideEmptyView = false, mBinding.edtWeather.text.isNullOrBlank())
        }

        isErrorMessageLive.observe(viewLifecycleOwner) {
            handleCommonError(it)
            adapter.submitList(emptyList())
            updateEmptyView(isHideEmptyView = false, mBinding.edtWeather.text.isNullOrBlank())
        }

        progressDialogEvent.observe(viewLifecycleOwner) {
            it ?: return@observe
            showProgressDialog(it, false)
        }

        isOnLineLive.observe(viewLifecycleOwner) {
            it ?: return@observe
            if (it) {
                queryWeather(mBinding.edtWeather.text)
            }
        }
    }

    private fun updateEmptyView(isHideEmptyView: Boolean, textEmpty: Boolean) = mBinding.run {
        rvWeather.isVisible = isHideEmptyView
        tvEmpty.isVisible = isHideEmptyView.not() && textEmpty.not()
        rvWeatherHistory.isVisible = isHideEmptyView.not() && textEmpty
    }

    @ExperimentalCoroutinesApi
    private fun initEvents() = mViewModel.run {
        textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //queryWeather(s)
                if (s.isNullOrBlank()) {
                    updateEmptyView(false, textEmpty = true)
                }
            }
        }
        btnGetWeather.setSafeOnClickListener {
            if (edtWeather.text.isNullOrBlank()) {
                updateEmptyView(false, textEmpty = true)
            } else if (mViewModel.isOnLine) {
                queryWeather(edtWeather.text)
            }
        }
    }

    private fun initUI() {
        adapter = WeatherListAdapter()
        mBinding.rvWeather.apply {
            adapter = this@HomeFragment.adapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        // init recyclerView for History
        adapterHistory = WeatherHistoryListAdapter()
        mBinding.rvWeatherHistory.apply {
            adapter = this@HomeFragment.adapterHistory
            addItemDecoration(WeatherItemDecorator(context))
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun handleCheckInFailed(status: Int, exception: String? = null) {
        exception ?: return
        showWarningDialog("$status", exception, -1)
    }

    private fun showWarningDialog(title: String = "", content: String, image: Int = -1) {
        simpleDialog = SimpleDialog.create(requireContext(), viewLifecycleOwner)
            .setTitle(title)
            .setContent(content)
            .setImageResource(image)
            .setPositiveLabel(getString(R.string.title_close))
            .also {
                it.show()
            }
    }

    private fun handleCommonError(error: Throwable) {
        if (error.message?.contains("404") == true) {
            Toast.makeText(context, getString(R.string.city_not_found), Toast.LENGTH_SHORT).show()
            return
        }
        val errorMsg = if (BuildConfig.DEBUG) error.message.orEmpty()
        else getString(R.string.content_unexpected_error)
        showWarningDialog(title = getString(R.string.title_unexpected_error), content = errorMsg)
    }

    override fun onStart() {
        super.onStart()
        connectivityProvider.addListener(this)
    }

    override fun onStop() {
        connectivityProvider.removeListener(this)
        super.onStop()
    }

    @SuppressLint("RestrictedApi")
    override fun onConnectivityStateChange(state: NetworkState) {
        val isConnect = state == NetworkState.Connected
        if (mViewModel.isOnLine != isConnect) {
            mViewModel.isOnLine = isConnect
        }
    }
}