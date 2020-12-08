package com.avalon.avalon.data.inservice

import okhttp3.Interceptor
import okhttp3.Response

class ApiInsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type","application/json")
            .build()

        return chain.proceed(request)
    }


}