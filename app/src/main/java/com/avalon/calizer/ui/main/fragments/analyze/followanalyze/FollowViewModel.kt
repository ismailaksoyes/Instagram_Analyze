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

    private val _unFollowersData = MutableStateFlow<UnFollowersState>(UnFollowersState.Empty)
    val unFollowersData:StateFlow<UnFollowersState> = _unFollowersData


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


    sealed class UnFollowersState{
        object Empty:UnFollowersState()
        object Loading:UnFollowersState()
        data class UpdateItem(val followData:List<FollowData>) :UnFollowersState()
        data class Success(val followData:List<FollowData>):UnFollowersState()
    }

    sealed class NewFollowersState{
        object Empty:NewFollowersState()
        object  Loading:NewFollowersState()
        data class UpdateItem(val followData: List<FollowData>):NewFollowersState()
        data class Success(val followData: List<FollowData>):NewFollowersState()
    }

    suspend fun getUnFollowers(userId:Long,position:Int){
        val unFollowers = followRepository.getNoFollowersData(userId,position)
        _unFollowersData.value = UnFollowersState.Success(unFollowers)
        _unFollowersData.value = UnFollowersState.UpdateItem(unFollowers)
    }

    suspend fun getFollowData(dataSize:Int){
        val data = followRepository.getFollowersData(dataSize)
        _allFollow.value = FollowState.Success(data)
        _allFollow.value = FollowState.UpdateItem(data)
    }
     fun updateAllFollowFlow(){
        _allFollow.value = FollowState.Loading
    }
    fun updateNoFollowFlow(){
        _unFollowersData.value = UnFollowersState.Loading
    }
    private suspend fun getCookies() =followRepository.getUserCookie().allCookie

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch {
        val cookies = getCookies()
        repository.getUserDetails(userId,cookies).let {
            if (it.isSuccessful){
                _updateFollow.value = UpdateState.Success(Resource.success(it.body()))
            }
        }
    }


}