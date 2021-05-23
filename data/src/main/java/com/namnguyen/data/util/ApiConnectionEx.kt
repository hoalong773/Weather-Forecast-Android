package com.namnguyen.data.util

import com.namnguyen.data.net.ApiConnection

inline fun <reified T : Any> ApiConnection.provideService(): T = retrofit.create(T::class.java)

inline fun <reified T : Any> ApiConnection.provideGeoService(): T = retrofitGeo.create(T::class.java)