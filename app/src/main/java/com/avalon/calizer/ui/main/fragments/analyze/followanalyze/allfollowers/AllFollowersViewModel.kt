package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllFollowersViewModel @Inject constructor(private val followRepository: FollowRepository, private val repository: Repository):
    ViewModel() {
    private val _allFollowers = MutableStateFlow<AllFollowersState>(
        AllFollowersState.Empty)
    val allFollowers: StateFlow<AllFollowersState> = _allFollowers

    private val _updateAllFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateAllFollowers: StateFlow<UpdateState> = _updateAllFollowers



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: Resource<ApiResponseUserDetails>):UpdateState()

    }
    sealed class AllFollowersState{
        object Empty :AllFollowersState()
        object Loading :AllFollowersState()
        data class UpdateItem(val followData:List<FollowersData>) :AllFollowersState()
        data class Success(val followData:List<FollowersData>) :AllFollowersState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val data = followRepository.getFollowers(dataSize)
            _allFollowers.value = AllFollowersState.Success(data)
            _allFollowers.value = AllFollowersState.UpdateItem(data)
        }
    }
    fun updateAllFollowFlow(){
        _allFollowers.value = AllFollowersState.Loading
    }

    private fun getCookies() = followRepository.getUserCookie()

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        val cookies = getCookies()
        repository.getUserDetails(userId,cookies).let {
            if (it.isSuccessful){
                _updateAllFollowers.value = UpdateState.Success(Resource.success(it.body()))
            }
        }
    }
}