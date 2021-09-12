package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.newfollowers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewFollowersViewModel @Inject constructor (private val followRepository: FollowRepository, private val repository: Repository):ViewModel(){
    private val _newFollowers = MutableStateFlow<NewFollowersState>(
        NewFollowersState.Empty)
    val newFollowers: StateFlow<NewFollowersState> = _newFollowers

    private val _updateNewFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateNewFollowers: StateFlow<UpdateState> = _updateNewFollowers



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: Resource<ApiResponseUserDetails>):UpdateState()

    }
    sealed class NewFollowersState{
        object Empty :NewFollowersState()
        data class UpdateItem(val followData:List<FollowersData>) :NewFollowersState()
        data class Success(val followData:List<FollowersData>) :NewFollowersState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch {
            val data = followRepository.getNewFollowers(dataSize)
            _newFollowers.value = NewFollowersState.Success(data)
            _newFollowers.value = NewFollowersState.UpdateItem(data)
        }

    }

    private suspend fun getCookies() =followRepository.getUserCookie()

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        val cookies = getCookies()
        repository.getUserDetails(userId,cookies).let {
            if (it.isSuccessful){
                _updateNewFollowers.value = UpdateState.Success(Resource.success(it.body()))
            }
        }
    }
}