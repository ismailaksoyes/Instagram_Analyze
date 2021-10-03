package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.*
import com.avalon.calizer.utils.Resource
import retrofit2.Response

interface ApiHelper{
    suspend fun getReelsTray(cookies:String?):Resource<ApiResponseReelsTray>
    suspend fun getUserDetails(userId:Long,cookies:String?):Resource<ApiResponseUserDetails>
    suspend fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Resource<ApiResponseUserFollow>
    suspend fun getUserFollowing(userId:Long,maxId: String?,rnkToken:String?,cookies: String?):Resource<ApiResponseUserFollow>
    suspend fun getStory(userId: Long,cookies: String?):Resource<ApiResponseStory>
    suspend fun getUserInfo(userName: String,cookies: String?):Resource<ApiResponseUserPk>
    suspend fun getHighlights(userId: Long,cookies: String?):Resource<ApiResponseHighlights>
    suspend fun getHighlightsStory(highlightId:String,cookies: String?):Resource<ApiResponseHighlightsStory>
    suspend fun getStoryViewer(storyId:String,cookies: String?,maxId:String?):Resource<ApiResponseStoryViewer>
    //usercase update details
}