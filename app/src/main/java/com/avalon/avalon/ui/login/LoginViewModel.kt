package com.avalon.avalon.ui.login

import androidx.lifecycle.*
import com.avalon.avalon.data.local.RoomData
import com.avalon.avalon.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val dbRepository: RoomRepository, private val repository: Repository) : ViewModel() {

    val cookies:MutableLiveData<RoomData> = MutableLiveData()
    val reelsTray :MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()


    fun addCookie(roomData: RoomData){
       viewModelScope.launch(Dispatchers.IO) {
          dbRepository.addCookie(roomData)
       }
    }

     fun getCookies(){
        viewModelScope.launch {
            val roomData:RoomData = dbRepository.getCookies()
            cookies.value = roomData
        }
    }

    suspend fun getReelsTray(url:String,cookies:String){
       viewModelScope.launch {
           val response:Response<ApiResponseReelsTray> = repository.getReelsTray(url,cookies)
           reelsTray.value = response
       }
    }

}