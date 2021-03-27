package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl  @Inject constructor(private val apiService: ApiService):ApiHelper {
    override suspend fun getReelsTray(cookies:String): Response<ApiResponseReelsTray> = apiService.getReelsTray(cookies)
    override suspend fun getUserDetails(cookies: String, userId: String): Response<ApiResponseUserDetails> = apiService.getUserDetails(cookies = cookies,userId = userId)


}