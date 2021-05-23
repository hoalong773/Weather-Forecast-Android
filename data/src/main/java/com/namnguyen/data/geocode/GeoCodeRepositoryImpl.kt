package com.namnguyen.data.geocode

import com.namnguyen.data.executor.PostExecutionThread
import com.namnguyen.data.util.flowRes
import com.namnguyen.data.util.toGeoCodeModel
import com.namnguyen.domain.base.BaseResponse
import com.namnguyen.domain.city.GeoCodeRepository
import com.namnguyen.domain.model.GeoCodeModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GeoCodeRepositoryImpl @Inject constructor(
    private val mGeoCodeService: GeocodeService,
    private val mPostExecutionThread: PostExecutionThread
) : GeoCodeRepository {

    @ExperimentalCoroutinesApi
    override fun getLocationByName(
        cityName: String,
        key: String
    ) = flowRes(mPostExecutionThread) {
        val response = mGeoCodeService.getLocationByName(
            cityName, key
        )
        BaseResponse(
            response.status?.code ?: 0,
            response.status?.toString().orEmpty(),
            "",
            response.results?.first()?.toGeoCodeModel() ?: GeoCodeModel(0.0, 0.0, "")
        )
    }.flowOn(mPostExecutionThread.io)
}