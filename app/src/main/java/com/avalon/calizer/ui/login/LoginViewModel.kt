package com.avalon.calizer.ui.login

import androidx.lifecycle.*
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val dbRepository: RoomRepository, private val repository: Repository) : ViewModel() {


    val reelsTray :MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()




    suspend fun getReelsTray(cookies:String){
       viewModelScope.launch {
           val response:Response<ApiResponseReelsTray> = repository.getReelsTray(cookies)
           reelsTray.value = response
       }
    }

}