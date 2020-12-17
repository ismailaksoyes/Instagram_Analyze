package com.avalon.avalon.data.inservice

import com.avalon.avalon.data.remote.insrequest.ApiRequestFollowers
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import retrofit2.Response
import retrofit2.http.*

interface ApiInsService {
    @POST("friendships/{userId}/followers/")
    suspend fun getFollowers(
        @Path("userId") userId:Int,
    )
    @GET()
   suspend fun getReelsTray(
        @Url url:String,
        @Header("Cookie") cookies:String
    ):Response<ApiResponseReelsTray>


}