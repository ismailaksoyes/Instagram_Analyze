package com.avalon.avalon.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.avalon.data.local.FollowersData
import com.avalon.avalon.data.local.LastFollowersData
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val dbRepository: RoomRepository, private val repository: Repository):ViewModel() {


    val allFollowers:MutableLiveData<Response<ApiResponseUserFollowers>> = MutableLiveData()

    fun addFollowers(followersData:List<FollowersData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addFollowers(followersData)
        }
    }
    fun addLastFollowers(followersData: List<LastFollowersData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addLastFollowers(followersData)
        }
    }
     fun getUserFollowers(userId:String,maxId: String?,rnkToken:String?, cookies: String){
        viewModelScope.launch {
            val response:Response<ApiResponseUserFollowers> = repository.getUserFollowers(userId,maxId,rnkToken,cookies)
            allFollowers.value = response

        }
    }



}