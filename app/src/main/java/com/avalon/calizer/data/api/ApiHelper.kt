package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import retrofit2.Response

interface ApiHelper{
    suspend fun getReelsTray():Response<ApiResponseReelsTray>
    suspend fun getUserDetails(userId:Long):Response<ApiResponseUserDetails>
    //usercase update details
}