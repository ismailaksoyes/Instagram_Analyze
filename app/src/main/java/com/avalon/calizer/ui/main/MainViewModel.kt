package com.avalon.calizer.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.*
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dbRepository: RoomRepository, private val repository: Repository):ViewModel() {


    val allFollow:MutableLiveData<Response<ApiResponseUserFollow>> = MutableLiveData()
   // val noFollow:MutableLiveData<FollowingData> = MutableLiveData()

    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData : StateFlow<UserDataFlow> = _userData

    private val _followersData = MutableStateFlow<FollowDataFlow>(FollowDataFlow.Empty)
    val followData : StateFlow<FollowDataFlow> = _followersData

    sealed class UserDataFlow{
        object Empty : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) :UserDataFlow()

    }

    sealed class FollowDataFlow{
        object Empty : FollowDataFlow()
        data class GetUserCookies(var accountsData: AccountsData):FollowDataFlow()
        data class GetFollowDataSync(var follow:  Resource<ApiResponseUserFollow>) :FollowDataFlow()
        data class GetFollowDataSuccess(var follow:  Resource<ApiResponseUserFollow>) :FollowDataFlow()
        object SaveFollow : FollowDataFlow()
        data class GetFollowingDataSync(var following:Resource<ApiResponseUserFollow>) :FollowDataFlow()
        data class GetFollowingDataSuccess(var following:Resource<ApiResponseUserFollow>) :FollowDataFlow()
        data class Error(val error: String) : FollowDataFlow()
    }

    fun getUserDetails(userId: Long){
        viewModelScope.launch {
            _userData.value = UserDataFlow.GetUserDetails(dbRepository.getUserInfo(userId))
        }
    }


    fun addFollow(followData:List<FollowData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addFollowData(followData)
        }
    }

    /**
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
    **/
     fun getUserFollowers(userId:Long,maxId: String?,rnkToken:String?, cookies: String?){

        viewModelScope.launch(Dispatchers.IO) {

            repository.getUserFollowers(userId,maxId,rnkToken,cookies).let {
                Log.d("ResponseData","${it.body()}")
                if (it.isSuccessful){

                    if (!it.body()?.nextMaxId.isNullOrEmpty()){
                        _followersData.value = FollowDataFlow.GetFollowDataSuccess(Resource.success(it.body()))
                       // _followersData.value = FollowDataFlow.GetFollowDataSync(Resource.success(it.body()))
                    }else{
                        _followersData.value = FollowDataFlow.GetFollowDataSuccess(Resource.success(it.body()))

                    }
                }else{
                    _followersData.value = FollowDataFlow.Error(it.errorBody().toString())
                }
            }

        }
    }
    fun getUserFollowing(userId:Long,maxId: String?,rnkToken:String?, cookies: String?){
        Log.d("StateSave", "getUserFollowing Request")

        viewModelScope.launch(Dispatchers.IO) {

            repository.getUserFollowing(userId,maxId,rnkToken,cookies).let {
                Log.d("StateSave", "getUserFollowing response")
                Log.d("ResponseData","${it.body()}")
                if (it.isSuccessful){

                    if (!it.body()?.nextMaxId.isNullOrEmpty()){

                        _followersData.value = FollowDataFlow.GetFollowingDataSync(Resource.success(it.body()))
                    }else{
                        Log.d("StateSave", "getUserFollowing OK VALUE")
                        _followersData.value = FollowDataFlow.GetFollowingDataSuccess(Resource.success(it.body()))
                        delay(1000)
                         _followersData.value = FollowDataFlow.SaveFollow
                    }
                }else{
                    _followersData.value = FollowDataFlow.Error(it.errorBody().toString())
                }
            }

        }
    }



}