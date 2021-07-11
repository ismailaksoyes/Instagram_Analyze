package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val followRepository: FollowRepository,private val repository: Repository):ViewModel() {

    private val _allFollow = MutableStateFlow<FollowState>(
        FollowState.Empty)
    val allFollow: StateFlow<FollowState> = _allFollow

    private val _updateFollow = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateFollow:StateFlow<UpdateState> = _updateFollow


    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: Resource<ApiResponseUserDetails>):UpdateState()

    }
    sealed class FollowState{
        object Empty :FollowState()
        object Loading :FollowState()
        data class UpdateItem(val followData:List<FollowData>) :FollowState()
        data class Success(val followData:List<FollowData>) :FollowState()

    }

    suspend fun getFollowData(dataSize:Int){
        val data = followRepository.getFollowersData(dataSize)
        _allFollow.value = FollowState.Success(data)
        _allFollow.value = FollowState.UpdateItem(data)
    }
     fun updateFlow(){
        _allFollow.value = FollowState.Loading
    }
    private suspend fun getCookies() =followRepository.getUserCookie().allCookie

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        val cookies = getCookies()
        repository.getUserDetails(cookies, userId).let {
            if (it.isSuccessful){
                _updateFollow.value = UpdateState.Success(Resource.success(it.body()))
            }
        }

    }


}