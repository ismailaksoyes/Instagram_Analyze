package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.notfollowers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotFollowersViewModel @Inject constructor (private val followRepository: FollowRepository, private val repository: Repository,private val prefs:MySharedPreferences):
    ViewModel(){
    private val _notFollowers = MutableStateFlow<NotFollowersState>(
        NotFollowersState.Empty)
    val notFollowers: StateFlow<NotFollowersState> = _notFollowers

    private val _updateNotFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateNotFollowers: StateFlow<UpdateState> = _updateNotFollowers



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: ApiResponseUserDetails):UpdateState()

    }
    sealed class NotFollowersState{
        object Empty :NotFollowersState()
        data class UpdateItem(val followData:List<FollowersData>) :NotFollowersState()
        data class Success(val followData:List<FollowersData>) :NotFollowersState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch {
            val data = followRepository.getNotFollowers(dataSize)
            _notFollowers.value = NotFollowersState.Success(data)
            _notFollowers.value = NotFollowersState.UpdateItem(data)
        }

    }


    suspend fun getUserDetails(userId: Long){
        viewModelScope.launch {
            when(val response = repository.getUserDetails(userId,prefs.allCookie)){
                is Resource.Success->{
                    response.data?.let { itData->
                        _updateNotFollowers.value = UpdateState.Success(itData)
                    }
                }
            }
        }
    }
}