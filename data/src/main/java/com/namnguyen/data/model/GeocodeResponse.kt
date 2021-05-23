package com.namnguyen.data.model

import com.google.gson.annotations.SerializedName

data class GeocodeResponse(
    @SerializedName("results")
    val results: List<Results?>?,
    @SerializedName("status")
    val status: Status?,
) {
    data class Results(
        @SerializedName("geometry")
        val geometry: Geometry?,
        @SerializedName("components")
        val components: Components?
    ) {
        data class Geometry(
            @SerializedName("lat")
            val lat: Double?,
            @SerializedName("lng")
            val lng: Double?
        )
        data class Components(
            @SerializedName("city")
            val city: String? = ""
        )
    }
    data class Status(
        @SerializedName("code")
        val code: Int? = 0,
        @SerializedName("message")
        val message: String?
    )
}