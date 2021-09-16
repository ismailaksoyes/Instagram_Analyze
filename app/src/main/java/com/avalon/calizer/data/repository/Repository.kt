package com.avalon.calizer.data.repository

import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.remote.insresponse.ApiResponseStory
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class Repository @Inject constructor(private val apiHelper: ApiHelper) {


    suspend fun getReelsTray(cookies:String?) = apiHelper.getReelsTray(cookies = cookies)

    suspend fun getUserDetails(userId: Long,cookies:String?) = apiHelper.getUserDetails(userId,cookies)

    suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowers(userId, maxId, rnkToken,cookies)

    suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowing(userId, maxId, rnkToken,cookies)
    suspend fun getStory(userId: Long,cookies: String?)=
        apiHelper.getStory(userId, cookies)

    /**
    suspend fun getStory(userId: Long,cookies: String?):Flow<Resource<ApiResponseStory>>{
        return flow {
            emit(apiHelper.getStory(userId, cookies))
        }.flowOn(ioDispatcher)
    }
    **/

}