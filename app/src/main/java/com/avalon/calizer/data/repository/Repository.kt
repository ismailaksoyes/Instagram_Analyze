package com.avalon.calizer.data.repository

import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.api.ApiClient
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {


    suspend fun getReelsTray(cookies: String) = apiHelper.getReelsTray(cookies)

    suspend fun getUserDetails(cookies: String,userId: Long) = apiHelper.getUserDetails(cookies,userId)

     suspend fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?, cookies: String?):Response<ApiResponseUserFollow>{
        return ApiClient.API.getFollowers(userId,maxId,rnkToken,cookies)
    }
    suspend fun getUserFollowing(userId:Long,maxId: String?,rnkToken:String?, cookies: String?):Response<ApiResponseUserFollow>{
        return ApiClient.API.getFollowing(userId,maxId,rnkToken,cookies)
    }



}