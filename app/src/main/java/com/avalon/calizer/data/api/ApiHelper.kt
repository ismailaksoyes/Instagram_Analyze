package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseStory
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.utils.Resource
import retrofit2.Response

interface ApiHelper{
    suspend fun getReelsTray(cookies:String?):Resource<ApiResponseReelsTray>
    suspend fun getUserDetails(userId:Long,cookies:String?):Resource<ApiResponseUserDetails>
    suspend fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Resource<ApiResponseUserFollow>
    suspend fun getUserFollowing(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Resource<ApiResponseUserFollow>
    suspend fun getStory(userId: Long,cookies: String?):Resource<ApiResponseStory>
    //usercase update details
}