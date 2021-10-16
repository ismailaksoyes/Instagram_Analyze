package com.avalon.calizer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.*
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.local.follow.OldFollowersData
import com.avalon.calizer.data.local.follow.OldFollowingData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollow
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dbRepository: RoomRepository,
    private val repository: Repository
) : ViewModel() {



    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData: StateFlow<UserDataFlow> = _userData

    private val _followersData = MutableStateFlow<FollowDataFlow>(FollowDataFlow.Empty)
    val followData: StateFlow<FollowDataFlow> = _followersData

    private val _saveFollowData = MutableStateFlow<FollowSaveState>(FollowSaveState.Empty)
    val saveFollowData:StateFlow<FollowSaveState> = _saveFollowData

    sealed class UserDataFlow {
        object Empty : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) : UserDataFlow()

    }

    sealed class FollowDataFlow {
        object Empty : FollowDataFlow()
        object StartAnalyze : FollowDataFlow()
        data class GetFollowersDataSync(var follow: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowersDataSuccess(var follow: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowingDataSync(var following: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowingDataSuccess(var following: ApiResponseUserFollow) : FollowDataFlow()
        data class Error(val error: String) : FollowDataFlow()
    }
    sealed class FollowSaveState{
        object Empty : FollowSaveState()
        data class SaveFollowers(val isSave: Boolean):FollowSaveState()
        data class SaveFollowing(val isSave: Boolean):FollowSaveState()
    }


    suspend fun getUserFollowers(userId: Long, maxId: String?, rnkToken: String?, cookies: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getUserFollowers(userId, maxId, rnkToken, cookies)){
                is Resource.Success->{
                    response.data?.let { itData->
                        if (!itData.nextMaxId.isNullOrEmpty()) {
                            _followersData.value = FollowDataFlow.GetFollowersDataSync(itData)
                        } else {
                            _followersData.value = FollowDataFlow.GetFollowersDataSuccess(itData)

                        }
                    }
                }
            }

        }
    }
     fun startAnalyze(){
        _followersData.value = FollowDataFlow.StartAnalyze
    }

    suspend fun getSaveFollowingType(){
        _saveFollowData.value = FollowSaveState.SaveFollowing(dbRepository.getSaveFollowingType())
    }

    suspend fun getSaveFollowersType(){
        _saveFollowData.value = FollowSaveState.SaveFollowers(dbRepository.getSaveFollowersType())
    }

    suspend fun getUserFollowing(userId: Long, maxId: String?, rnkToken: String?, cookies: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getUserFollowing(userId, maxId, rnkToken, cookies)){
                is Resource.Success->{
                    response.data?.let { itData->
                        if (!itData.nextMaxId.isNullOrEmpty()) {
                            _followersData.value =
                                FollowDataFlow.GetFollowingDataSync(itData)
                        } else {
                            _followersData.value =
                                FollowDataFlow.GetFollowingDataSuccess(itData)
                        }
                    }
                }
            }
        }
    }


    suspend fun addFollowersData(followersData:List<FollowersData> ){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFollowersData()
            dbRepository.addFollowersData(followersData)
        }
    }
    suspend fun addFollowingData(followingData:List<FollowingData> ){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFollowingData()
            dbRepository.addFollowingData(followingData)
        }
    }
    suspend fun updateFollowersSaveType(){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.updateFollowersSaveType()
        }
    }
    suspend fun updateFollowingSaveType(){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.updateFollowingSaveType()
        }
    }
    suspend fun addOldFollowersData(oldFollowersData:List<OldFollowersData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addOldFollowersData(oldFollowersData)
        }
    }
    suspend fun addOldFollowingData(oldFollowingData:List<OldFollowingData>){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addOldFollowingData(oldFollowingData)
        }
    }



}