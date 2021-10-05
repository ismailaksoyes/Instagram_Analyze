package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
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

class AllFollowersViewModel @Inject constructor(
    private val followRepository: FollowRepository,
    private val repository: Repository,
    private val prefs: MySharedPreferences
) :
    ViewModel() {
    private val _allFollowers = MutableStateFlow<AllFollowersState>(
        AllFollowersState.Empty
    )
    val allFollowers: StateFlow<AllFollowersState> = _allFollowers

    private val _updateAllFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateAllFollowers: StateFlow<UpdateState> = _updateAllFollowers


    sealed class UpdateState {
        object Empty : UpdateState()
        data class Success(var userDetails: ApiResponseUserDetails) : UpdateState()

    }

    sealed class AllFollowersState {
        object Empty : AllFollowersState()
        data class UpdateItem(val followData: List<FollowersData>) : AllFollowersState()
        data class Success(val followData: List<FollowersData>) : AllFollowersState()

    }

    suspend fun getFollowData(dataSize: Int) {
        viewModelScope.launch {
            val data = followRepository.getFollowers(dataSize)
            _allFollowers.value = AllFollowersState.Success(data)
            _allFollowers.value = AllFollowersState.UpdateItem(data)

        }

    }

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        when(val response = repository.getUserDetails(userId,prefs.allCookie)){
            is Resource.Success->{
                response.data?.let {  itResponse->
                    _updateAllFollowers.value = UpdateState.Success(itResponse)
                }
            }
        }
    }
}