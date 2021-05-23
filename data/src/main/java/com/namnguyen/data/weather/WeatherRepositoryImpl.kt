package com.namnguyen.data.weather

import com.namnguyen.data.executor.PostExecutionThread
import com.namnguyen.data.util.flowRes
import com.namnguyen.data.util.toWeatherModels
import com.namnguyen.domain.base.BaseResponse
import com.namnguyen.domain.city.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val mWeatherListService: WeatherListService,
    private val mPostExecutionThread: PostExecutionThread
) : WeatherRepository {

    @ExperimentalCoroutinesApi
    override fun getListWeather(
        lat: Double,
        lng: Double,
        appId: String,
        exclude: Int
    ) = flowRes(mPostExecutionThread) {
        val response = mWeatherListService.getWeatherForecast(
            lat, lng, appId, exclude
        )
        BaseResponse(
            200,
            "",
            "",
            response.listWeatherForecast?.toWeatherModels() ?: emptyList()
        )
    }.flowOn(mPostExecutionThread.io)
}