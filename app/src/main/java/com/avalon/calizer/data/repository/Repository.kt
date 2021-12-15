package com.avalon.calizer.data.repository

import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.remote.insrequest.ApiTranslation
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

    suspend fun getTranslation(lang:String) = apiHelper.getTranslation(url = "https://avmogame.com/calizer/api/translation/translation.php",lang)

    suspend fun getReelsTray(cookies:String?) = apiHelper.getReelsTray(cookies = cookies)

    suspend fun getUserDetails(userId: Long,cookies:String?) = apiHelper.getUserDetails(userId,cookies)

    suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowers(userId, maxId, rnkToken,cookies)

    suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?,cookies:String?) =
        apiHelper.getUserFollowing(userId, maxId, rnkToken,cookies)
    suspend fun getStory(userId: Long,cookies: String?)=
        apiHelper.getStory(userId, cookies)

    suspend fun getUserPk(username:String,cookies: String?) = apiHelper.getUserInfo(username,cookies)

    suspend fun getHighlights(userId: Long,cookies: String?) = apiHelper.getHighlights(userId, cookies)

    suspend fun getHighlightsStory(highlightId:String,cookies: String?) = apiHelper.getHighlightsStory(highlightId, cookies)

    suspend fun getStoryViewer(storyId:String,cookies: String?,maxId:String?) = apiHelper.getStoryViewer(storyId, cookies, maxId)

    /**
    suspend fun getStory(userId: Long,cookies: String?):Flow<Resource<ApiResponseStory>>{
        return flow {
            emit(apiHelper.getStory(userId, cookies))
        }.flowOn(ioDispatcher)
    }
    **/

}