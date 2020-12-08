package com.avalon.avalon.data.inservice

import com.avalon.avalon.data.remote.insrequest.ApiRequestFollowers
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiInsService {
    @POST()
    fun getUserList(
        @Url url:String,
        @Body request:ApiRequestFollowers
    )


}