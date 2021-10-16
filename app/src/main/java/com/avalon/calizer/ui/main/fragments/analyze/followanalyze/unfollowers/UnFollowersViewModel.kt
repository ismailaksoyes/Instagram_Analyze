package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.unfollowers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnFollowersViewModel  @Inject constructor (private val followRepository: FollowRepository, private val repository: Repository,private val prefs:MySharedPreferences):
    ViewModel(){

    private val _unFollowers = MutableStateFlow<UnFollowersState>(
        UnFollowersState.Empty)
    val unFollowers: StateFlow<UnFollowersState> = _unFollowers

    private val _updateUnFollowers = MutableStateFlow<UpdateState>(UpdateState.Empty)
    val updateUnFollowers: StateFlow<UpdateState> = _updateUnFollowers



    sealed class UpdateState{
        object Empty:UpdateState()
        data class Success(var userDetails: ApiResponseUserDetails):UpdateState()

    }
    sealed class UnFollowersState{
        object Empty :UnFollowersState()
        data class UpdateItem(val followData:List<FollowersData>) :UnFollowersState()
        data class Success(val followData:List<FollowersData>) :UnFollowersState()

    }

    suspend fun getFollowData(dataSize:Int){
        viewModelScope.launch {
            val data = followRepository.getUnFollowers(dataSize)
            _unFollowers.value = UnFollowersState.Success(data)
            _unFollowers.value = UnFollowersState.UpdateItem(data)
        }
    }


    suspend fun getUserDetails(userId: Long){
        viewModelScope.launch {
          when(val response = repository.getUserDetails(userId,prefs.allCookie)){
              is Resource.Success->{
                  response.data?.let { itData->
                      _updateUnFollowers.value = UpdateState.Success(itData)
                  }
              }

          }
        }
    }



}