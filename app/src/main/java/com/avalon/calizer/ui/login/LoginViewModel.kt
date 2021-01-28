package com.avalon.calizer.ui.login

import androidx.lifecycle.*
import com.avalon.calizer.data.local.RoomData
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
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

    suspend fun getReelsTray(cookies:String){
       viewModelScope.launch {
           val response:Response<ApiResponseReelsTray> = repository.getReelsTray(cookies)
           reelsTray.value = response
       }
    }

}