package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import retrofit2.Response

interface ApiHelper{
    suspend fun getReelsTray(cookies:String):Response<ApiResponseReelsTray>
}