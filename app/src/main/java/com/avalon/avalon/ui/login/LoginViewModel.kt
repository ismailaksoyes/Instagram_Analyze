package com.avalon.avalon.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.local.CookieDatabase
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val dbRepository: CookieRepository,private val repository: Repository) : ViewModel() {

    val cookies:MutableLiveData<CookieData> = MutableLiveData()
    val reelsTray :MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()


    fun addCookie(cookieData: CookieData){
       viewModelScope.launch(Dispatchers.IO) {
          dbRepository.addCookie(cookieData)
       }
    }

     fun getCookies(){
        viewModelScope.launch {
            val cookieData:CookieData = dbRepository.getCookies()
            cookies.value = cookieData
        }
    }

    suspend fun getReelsTray(url:String,cookies:String){
       viewModelScope.launch {
           val response:Response<ApiResponseReelsTray> = repository.getReelsTray(url,cookies)
           reelsTray.value = response
       }
    }

}