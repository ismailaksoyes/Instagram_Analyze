package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.utils.MySharedPreferences
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl  @Inject constructor(private val apiService: ApiService, private val prefs:MySharedPreferences):ApiHelper {
    override suspend fun getReelsTray(): Response<ApiResponseReelsTray> = apiService.getReelsTray(prefs.allCookie)
    override suspend fun getUserDetails(userId: Long): Response<ApiResponseUserDetails> = apiService.getUserDetails(cookies = prefs.allCookie,userId = userId)


}