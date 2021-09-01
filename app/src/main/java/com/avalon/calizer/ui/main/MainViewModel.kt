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
        data class GetUserCookies(var accountsData: AccountsData) : FollowDataFlow()
        data class GetFollowersDataSync(var follow: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowersDataSuccess(var follow: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowingDataSync(var following: ApiResponseUserFollow) : FollowDataFlow()
        data class GetFollowingDataSuccess(var following: ApiResponseUserFollow) : FollowDataFlow()
        data class SaveFollow(var userInfo: AccountsInfoData) : FollowDataFlow()
        data class Error(val error: String) : FollowDataFlow()
    }
    sealed class FollowSaveState{
        object Empty : FollowSaveState()
        data class SaveFollowers(val accountsData: AccountsData):FollowSaveState()
        data class SaveFollowing(val accountsData: AccountsData):FollowSaveState()
    }


    fun addFollow(followData: List<FollowData>,userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addFollowData(followData,userId)
        }
    }

    suspend fun getUserFollowers(
        userId: Long,
        maxId: String?,
        rnkToken: String?,
        cookies: String?
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            repository.getUserFollowers(userId, maxId, rnkToken,cookies).let { itUserFollowers->
                if (itUserFollowers.isSuccessful) {

                    itUserFollowers.body()?.let { itFollowersBody->
                        if (itFollowersBody.nextMaxId.isNotEmpty()) {
                            _followersData.value = FollowDataFlow.GetFollowersDataSync(itFollowersBody)
                        } else {
                            _followersData.value =
                                FollowDataFlow.GetFollowersDataSuccess(itFollowersBody)

                        }
                    }

                } else {
                    _followersData.value = FollowDataFlow.Error(itUserFollowers.message())
                }
            }

        }
    }

    suspend fun getUserFollowing(
        userId: Long,
        maxId: String?,
        rnkToken: String?,
        cookies: String?
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserFollowing(userId, maxId, rnkToken,cookies).let { itUserFollowing->
                if (itUserFollowing.isSuccessful) {
                    itUserFollowing.body()?.let { itFollowingBody->
                        if (itFollowingBody.nextMaxId.isNotEmpty()) {
                            _followersData.value =
                                FollowDataFlow.GetFollowingDataSync(itFollowingBody)
                        } else {

                            _followersData.value =
                                FollowDataFlow.GetFollowingDataSuccess(itFollowingBody)
                        }
                    }


                } else {
                    _followersData.value = FollowDataFlow.Error(itUserFollowing.message())
                }
            }

        }
    }


    suspend fun setSaveFollowData(followData: List<FollowData>,userId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addFollowData(followData,userId)
        }
    }
    suspend fun updateUserType(userId:Long,followersType:Long,followingType:Long){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.updateUserType(userId,followersType, followingType)
        }

    }


}