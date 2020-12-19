package com.avalon.avalon.data.repository

import androidx.lifecycle.ViewModel
import com.avalon.avalon.data.inservice.ApiInsClient
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import retrofit2.Response

class Repository {
    suspend fun getReelsTray(url:String,cookies:String):Response<ApiResponseReelsTray>{
        return ApiInsClient.api.getReelsTray(url,cookies)
    }

    suspend fun getUserFollowers(userId:Long,cookies: String):Response<ApiResponseUserFollowers>{
        return ApiInsClient.api.getFollowers(userId,cookies)
    }

}