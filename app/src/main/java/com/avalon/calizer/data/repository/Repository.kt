package com.avalon.calizer.data.repository

import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiHelper: ApiHelper) {


    suspend fun getReelsTray(cookies:String?) = apiHelper.getReelsTray(cookies = cookies)

    suspend fun getUserDetails(userId: Long,cookies:String?) = apiHelper.getUserDetails(userId,cookies)

    suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowers(userId, maxId, rnkToken,cookies)

    suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowing(userId, maxId, rnkToken,cookies)


}