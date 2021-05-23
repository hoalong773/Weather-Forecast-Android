package com.namnguyen.data.geocode

import com.namnguyen.data.model.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeService {
    @GET("geocode/v1/json")
    suspend fun getLocationByName(
        @Query("q") cityName: String,
        @Query("key") key: String,
        @Query("language") language: String = "en",
        @Query("pretty") pretty: Int = 1,
        @Query("no_annotations") no_annotations: Int = 1,
        @Query("no_record") no_record: Int = 1,
        @Query("litmit") litmit: Int = 1,
    ): GeocodeResponse
}