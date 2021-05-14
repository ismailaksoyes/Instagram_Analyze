package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("friendships/{userId}/followers/")
    suspend fun getFollowers(
        @Path("userId") userId:Long,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserFollow>
    @GET("friendships/{userId}/following/")
    suspend fun getFollowing(
        @Path("userId") userId:Long,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserFollow>
    @GET("feed/reels_tray/")
   suspend fun getReelsTray(
        @Header("Cookie") cookies:String?
    ):Response<ApiResponseReelsTray>

    @GET("users/{userId}/info/")
    suspend fun getUserDetails(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserDetails>



}