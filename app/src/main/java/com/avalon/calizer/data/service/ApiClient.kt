package com.avalon.calizer.data.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(ApiInterceptor())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("https://avmogame.com/analyzes/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    val api: ApiService by lazy {
        retrofit.create(
                ApiService::class.java
        )

    }
}