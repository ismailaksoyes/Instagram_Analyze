package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotFollowStoryViewsViewModel @Inject constructor(val roomRepo: RoomRepository,prefs:MySharedPreferences) :ViewModel() {
    private val _followData = MutableStateFlow<NotStoryState>(NotStoryState.Empty)
    val followData:StateFlow<NotStoryState> = _followData

    sealed class NotStoryState{
        object Empty:NotStoryState()
        object Loading:NotStoryState()
        object Error:NotStoryState()
        data class Success(val followersData: List<FollowersData>):NotStoryState()
    }

    suspend fun getFollowers(){
        viewModelScope.launch {
            val response = roomRepo.getAllFollowers()
            if (response.isNotEmpty()){
                _followData.value = NotStoryState.Success(response)
            }else{
                _followData.value = NotStoryState.Error
            }
        }
    }

    fun setLoadingState(){
        _followData.value = NotStoryState.Loading
    }


}