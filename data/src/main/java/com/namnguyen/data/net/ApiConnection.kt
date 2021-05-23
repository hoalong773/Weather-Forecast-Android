package com.namnguyen.data.net

import com.namnguyen.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val OK_HTTP_TIMEOUT = 15 // SECONDS

class ApiConnection {
    val retrofit: Retrofit
    val retrofitGeo: Retrofit
    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    )
            )
            .retryOnConnectionFailure(true)
            .followRedirects(true)
            .connectTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitGeo = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_GEOCODE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
