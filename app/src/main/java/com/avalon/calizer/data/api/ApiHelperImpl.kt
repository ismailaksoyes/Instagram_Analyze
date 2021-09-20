package com.avalon.calizer.data.api

import com.avalon.calizer.data.error.NETWORK_ERROR
import com.avalon.calizer.data.error.NO_INTERNET_CONNECTION
import com.avalon.calizer.data.remote.insresponse.*
import com.avalon.calizer.utils.NetworkConnectivity
import com.avalon.calizer.utils.Resource
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction2

class ApiHelperImpl  @Inject constructor(private val apiService: ApiService, private val networkConnectivity: NetworkConnectivity):ApiHelper {
    override suspend fun getReelsTray(cookies:String?): Resource<ApiResponseReelsTray> {
        return when(val response = checkResponse { (apiService::getReelsTray)(cookies) }){
            is ApiResponseReelsTray->Resource.Success(data = response)
            else->Resource.DataError(errorCode = response as Int)
        }
    }
    override suspend fun getUserDetails(userId: Long,cookies:String?): Resource<ApiResponseUserDetails>{
        return when(val response = checkResponse { (apiService::getUserDetails)(userId, cookies) }){
            is ApiResponseUserDetails->Resource.Success(data = response)
            else->Resource.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?,cookies:String?): Resource<ApiResponseUserFollow> {
        return when(val response = checkResponse { (apiService::getFollowers)(userId, maxId, rnkToken, cookies) }){
            is ApiResponseUserFollow-> Resource.Success(data = response)
            else-> Resource.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?,cookies:String?): Resource<ApiResponseUserFollow>{
        return when(val response= checkResponse { (apiService::getFollowing)(userId, maxId, rnkToken, cookies) }){
            is ApiResponseUserFollow-> Resource.Success(data = response)
            else-> Resource.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getStory(userId: Long, cookies: String?): Resource<ApiResponseStory> {
        return when(val response = checkResponse { (apiService::getStory)(userId, cookies) }){
            is ApiResponseStory-> Resource.Success(data = response)
            else-> Resource.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getUserPk(url: String): Resource<ApiResponseUserPk> {
        return when(val response = checkResponse { (apiService::getUserPk)(url) }){
            is ApiResponseUserPk->Resource.Success(data = response)
            else-> Resource.DataError(errorCode = response as Int)
        }
    }

    private suspend fun checkResponse(call:suspend() -> Response<*>):Any?{
        if (!networkConnectivity.isConnected()){
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = call.invoke()
            val responseCode = response.code()
            if (response.isSuccessful){
                response.body()
            }else{
               responseCode
            }
        } catch (e:IOException){
            NETWORK_ERROR
        }
    }

}