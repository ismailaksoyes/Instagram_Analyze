package com.avalon.calizer.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var logging:HttpLoggingInterceptor = HttpLoggingInterceptor()
    val suc = logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().apply {
        addNetworkInterceptor(suc)
        addInterceptor(ApiInterceptor())

            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://i.instagram.com/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val  API: ApiService by lazy {
        retrofit.create(
          ApiService::class.java
        )

    }
}