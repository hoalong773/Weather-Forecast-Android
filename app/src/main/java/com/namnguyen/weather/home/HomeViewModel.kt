package com.namnguyen.weather.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namnguyen.data.cache.AppSharePref
import com.namnguyen.data.executor.PostExecutionThread
import com.namnguyen.domain.base.BaseResult
import com.namnguyen.domain.city.GeoCodeRepository
import com.namnguyen.domain.city.WeatherRepository
import com.namnguyen.domain.model.GeoCodeModel
import com.namnguyen.domain.model.WeatherModel
import com.namnguyen.weather.base.BaseViewModel
import com.namnguyen.weather.ext.SingleLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val DEBOUNCE_TIME = 700L
const val FORECAST_DAY = 7
const val MIN_SEARCH_LENGTH = 1

class HomeViewModel @Inject constructor(
    private val mGeocodeRepository: GeoCodeRepository,
    private val mWeatherRepository: WeatherRepository,
    private val mSharePref: AppSharePref,
    mPostExecutionThread: PostExecutionThread
) : BaseViewModel() {

    @ExperimentalCoroutinesApi
    val mQueryStringLive = MutableStateFlow("")

    val isOnLineLive = SingleLiveData<Boolean>()
    var isOnLine: Boolean
        get() = isOnLineLive.value == true
        set(value) {
            isOnLineLive.postValue(value)
        }

    val listWeatherLiveData = MutableLiveData<List<WeatherModel>>()
    val isFailMessageLive = SingleLiveData<Pair<Int, String>>()
    val isErrorMessageLive = SingleLiveData<Throwable>()

    val mListHistoryCityLive = SingleLiveData<ArrayList<String>>()

    init {
        getHistoryCity()
    }

    private fun getHistoryCity() {
        try {
            val listStringType = object : TypeToken<ArrayList<String>>() {}.type
            val listHistoryCity: ArrayList<String> = Gson().fromJson(
                mSharePref.jsonHistoryName,
                listStringType
            )
            mListHistoryCityLive.postValue(listHistoryCity)
            Log.d("NamNguyen", "getHistoryCity: $listHistoryCity")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setHistoryCity(list: ArrayList<String>) {
        try {
            mSharePref.jsonHistoryName = Gson().toJson(list)
            getHistoryCity()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @ExperimentalCoroutinesApi
    fun queryWeather(query: CharSequence?) {
        val iQuery = query?.toString()?.trim().orEmpty()
        if (iQuery == mQueryStringLive.value) return
        mQueryStringLive.value = iQuery
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val geoCodeResultLive = mQueryStringLive
        .debounce(DEBOUNCE_TIME)
        .flatMapLatest {
            if (it.isNotEmpty() && it.length >= MIN_SEARCH_LENGTH && isOnLine) {
                mGeocodeRepository.getLocationByName(it, mSharePref.geocodeKey)
            } else {
                emptyFlow()
            }
        }
        .flowOn(mPostExecutionThread.io)
        .catch {
            flowOf(GeoCodeModel(0.0, 0.0, ""))
            it.printStackTrace()
        }
        .asLiveData(viewModelScope.coroutineContext)

    fun getWeatherForeCast(
        lat: Double,
        lng: Double
    ) {
        mWeatherRepository.getListWeather(
            lat, lng, mSharePref.appId, FORECAST_DAY
        ).onEach {
            when (it) {
                is BaseResult.Loading -> {
                    showProgress()
                }
                is BaseResult.Success -> {
                    it.data.let { weatherModels ->
                        listWeatherLiveData.postValue(weatherModels)
                    }
                    hideProgress()
                }
                is BaseResult.Failed -> {
                    hideProgress()
                    isFailMessageLive.postValue(it.status to it.exception)
                }
                is BaseResult.Error -> {
                    hideProgress()
                    isErrorMessageLive.postValue(it.error)
                }
                else -> {
                }
            }
        }.catch {
            it.printStackTrace()
        }.launchIn(viewModelScope)
    }
}