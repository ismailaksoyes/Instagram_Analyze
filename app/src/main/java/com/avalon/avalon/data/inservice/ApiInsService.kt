package com.avalon.avalon.data.inservice

import com.avalon.avalon.data.remote.insrequest.ApiRequestFollowers
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import retrofit2.Response
import retrofit2.http.*

interface ApiInsService {
    @GET()
    suspend fun getFollowers(
        @Url url: String,
        @Header("Cookie") cookies: String
    ):Response<ApiResponseUserFollowers>
    @GET()
   suspend fun getReelsTray(
        @Url url:String,
        @Header("Cookie") cookies:String
    ):Response<ApiResponseReelsTray>


}