package com.avalon.calizer.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SplashViewModel @ViewModelInject constructor(private val repository: Repository):ViewModel() {
    val reelsTray : MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()

    suspend fun getReelsTray(cookies:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response:Response<ApiResponseReelsTray> = repository.getReelsTray(cookies)
            reelsTray.value = response
        }
    }
}