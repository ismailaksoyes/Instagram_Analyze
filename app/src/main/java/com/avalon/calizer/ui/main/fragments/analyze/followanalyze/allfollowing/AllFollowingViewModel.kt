package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllFollowingViewModel @Inject constructor(private val followRepository: FollowRepository, private val repository: Repository,private val prefs:MySharedPreferences):
    ViewModel() {
    private val _allFollowing = MutableStateFlow<AllFollowingState>(
        AllFollowingState.Empty)
    val allFollowing : StateFlow<AllFollowingState> = _allFollowing

    private val _updateAllFollowing = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateAllFollowing: StateFlow<UpdateState> = _updateAllFollowing



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: ApiResponseUserDetails):UpdateState()

    }
    sealed class AllFollowingState{
        object Empty :AllFollowingState()
        data class UpdateItem(val followData:List<FollowingData>) :AllFollowingState()
        data class Success(val followData:List<FollowingData>) :AllFollowingState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch{
            val data = followRepository.getFollowing(dataSize)
            _allFollowing.value = AllFollowingState.Success(data)
            _allFollowing.value = AllFollowingState.UpdateItem(data)
        }

    }


    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        when(val response = repository.getUserDetails(userId,prefs.allCookie)){
            is Resource.Success->{
                response.data?.let { itResponse->
                    _updateAllFollowing.value = UpdateState.Success(itResponse)
                }
            }
        }

    }
}