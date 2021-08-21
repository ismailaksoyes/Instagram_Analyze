package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import retrofit2.Response

interface ApiHelper{
    suspend fun getReelsTray(cookies:String?):Response<ApiResponseReelsTray>
    suspend fun getUserDetails(userId:Long,cookies:String?):Response<ApiResponseUserDetails>
    suspend fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Response<ApiResponseUserFollow>
    suspend fun getUserFollowing(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Response<ApiResponseUserFollow>
    //usercase update details
}