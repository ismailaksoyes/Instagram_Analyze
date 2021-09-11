package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.unfollowers

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

class UnFollowersViewModel  @Inject constructor (private val followRepository: FollowRepository, private val repository: Repository):
    ViewModel(){

    private val _unFollowers = MutableStateFlow<UnFollowersState>(
        UnFollowersState.Empty)
    val unFollowers: StateFlow<UnFollowersState> = _unFollowers

    private val _updateUnFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateUnFollowers: StateFlow<UpdateState> = _updateUnFollowers



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: Resource<ApiResponseUserDetails>):UpdateState()

    }
    sealed class UnFollowersState{
        object Empty :UnFollowersState()
        object Loading :UnFollowersState()
        data class UpdateItem(val followData:List<FollowersData>) :UnFollowersState()
        data class Success(val followData:List<FollowersData>) :UnFollowersState()

    }

    suspend fun getFollowData(dataSize:Int){
        val data = followRepository.getUnFollowers(dataSize)
        _unFollowers.value = UnFollowersState.Success(data)
        _unFollowers.value = UnFollowersState.UpdateItem(data)
    }
    fun updateUnFollowFlow(){
        _unFollowers.value = UnFollowersState.Loading
    }

    private suspend fun getCookies() =followRepository.getUserCookie()

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        val cookies = getCookies()
        repository.getUserDetails(userId,cookies).let {
            if (it.isSuccessful){
                _updateUnFollowers.value = UpdateState.Success(Resource.success(it.body()))
            }
        }
    }



}