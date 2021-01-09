package com.avalon.avalon.data.inservice

import com.avalon.avalon.data.remote.insrequest.ApiRequestFollowers
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import retrofit2.Response
import retrofit2.http.*

interface ApiInsService {
    @GET("friendships/{userId}/followers/")
    suspend fun getFollowers(
        @Path("userId") userId:String,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String
    ):Response<ApiResponseUserFollowers>
    @GET("friendships/{userId}/following/")
    suspend fun getFollowing(
        @Path("userId") userId:String,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String
    ):Response<ApiResponseUserFollowers>
    @GET("feed/reels_tray/")
   suspend fun getReelsTray(
        @Header("Cookie") cookies:String
    ):Response<ApiResponseReelsTray>


}