package com.avalon.calizer.data.repository

import com.avalon.calizer.data.inservice.ApiInsClient
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import retrofit2.Response

class Repository {
     suspend fun getReelsTray(cookies:String):Response<ApiResponseReelsTray>{
        return ApiInsClient.api.getReelsTray(cookies)
    }

     suspend fun getUserFollowers(userId:String,maxId: String?,rnkToken:String?, cookies: String):Response<ApiResponseUserFollowers>{
        return ApiInsClient.api.getFollowers(userId,maxId,rnkToken,cookies)
    }
    suspend fun getUserFollowing(userId:String,maxId: String?,rnkToken:String?, cookies: String):Response<ApiResponseUserFollowers>{
        return ApiInsClient.api.getFollowing(userId,maxId,rnkToken,cookies)
    }

}