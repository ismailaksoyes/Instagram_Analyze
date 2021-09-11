package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllFollowingViewModel @Inject constructor(private val followRepository: FollowRepository, private val repository: Repository):
    ViewModel() {
    private val _allFollowing = MutableStateFlow<AllFollowingState>(
        AllFollowingState.Empty)
    val allFollowing : StateFlow<AllFollowingState> = _allFollowing

    private val _updateAllFollowing = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateAllFollowing: StateFlow<UpdateState> = _updateAllFollowing



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: Resource<ApiResponseUserDetails>):UpdateState()

    }
    sealed class AllFollowingState{
        object Empty :AllFollowingState()
        object Loading :AllFollowingState()
        data class UpdateItem(val followData:List<FollowingData>) :AllFollowingState()
        data class Success(val followData:List<FollowingData>) :AllFollowingState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch(Dispatchers.IO){
            val data = followRepository.getFollowing(dataSize)
            _allFollowing.value = AllFollowingState.Success(data)
            _allFollowing.value = AllFollowingState.UpdateItem(data)
        }

    }
    fun updateAllFollowFlow(){
        _allFollowing.value = AllFollowingState.Loading
    }

    private  fun getCookies() =followRepository.getUserCookie()

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        val cookies = getCookies()
        repository.getUserDetails(userId,cookies).let {
            if (it.isSuccessful){
                _updateAllFollowing.value = UpdateState.Success(Resource.success(it.body()))
            }
        }
    }
}