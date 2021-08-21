package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.utils.MySharedPreferences
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl  @Inject constructor(private val apiService: ApiService):ApiHelper {
    override suspend fun getReelsTray(cookies:String?): Response<ApiResponseReelsTray> = apiService.getReelsTray(cookies = cookies)
    override suspend fun getUserDetails(userId: Long,cookies:String?): Response<ApiResponseUserDetails> = apiService.getUserDetails(cookies = cookies,userId = userId)
    override suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?,cookies:String?): Response<ApiResponseUserFollow> = apiService.getFollowers(userId = userId,maxId = maxId,rnkToken = rnkToken,cookies = cookies)
    override suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?,cookies:String?): Response<ApiResponseUserFollow> = apiService.getFollowing(userId = userId,maxId = maxId,rnkToken = rnkToken,cookies = cookies)

}