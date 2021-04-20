package com.avalon.calizer.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.FollowersData
import com.avalon.calizer.data.local.FollowingData
import com.avalon.calizer.data.local.LastFollowersData
import com.avalon.calizer.data.local.LastFollowingData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dbRepository: RoomRepository, private val repository: Repository):ViewModel() {


    val allFollowers:MutableLiveData<Response<ApiResponseUserFollowers>> = MutableLiveData()
    val noFollow:MutableLiveData<FollowingData> = MutableLiveData()

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

    fun addFollowing(followingData: List<FollowingData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addFollowing(followingData)
        }
    }
    fun addLastFollowing(followingData: List<LastFollowingData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addLastFollowing(followingData)
        }
    }

    fun getNotFollow(){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.getNotFollow()
        }
    }
     fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?, cookies: String?){
        viewModelScope.launch {
            val response:Response<ApiResponseUserFollowers> = repository.getUserFollowers(userId,maxId,rnkToken,cookies)
            allFollowers.value = response

        }
    }



}