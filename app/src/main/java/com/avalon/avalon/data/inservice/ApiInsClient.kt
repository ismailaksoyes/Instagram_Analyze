package com.avalon.avalon.data.inservice

import com.avalon.avalon.data.service.ApiInterceptor
import com.avalon.avalon.data.service.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInsClient {
    private var logging:HttpLoggingInterceptor = HttpLoggingInterceptor()
    val suc = logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().apply {
        addNetworkInterceptor(suc)
        addInterceptor(ApiInsInterceptor())

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
    val  api: ApiInsService by lazy {
        retrofit.create(
          ApiInsService::class.java
        )

    }
}