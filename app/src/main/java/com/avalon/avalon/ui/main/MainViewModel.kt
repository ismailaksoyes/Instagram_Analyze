package com.avalon.avalon.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val dbRepository: CookieRepository, private val repository: Repository):ViewModel() {

    val cookies:MutableLiveData<CookieData> = MutableLiveData()
    val allFollowers:MutableLiveData<Response<ApiResponseUserFollowers>> = MutableLiveData()


    fun getUserFollowers(userId:Long,cookies:String){
        viewModelScope.launch {
            val response:Response<ApiResponseUserFollowers> = repository.getUserFollowers(userId,cookies)
            allFollowers.value = response
        }
    }
    fun getCookies(){
        viewModelScope.launch {
            val cookieData:CookieData = dbRepository.getCookies()
            cookies.value = cookieData
        }
    }

}