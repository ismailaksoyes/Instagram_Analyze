package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.shared.model.LocalizationType
import com.avalon.calizer.shared.model.LocalizationType.ANALYZE_ALLFOLLOWERS_TITLE
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFollowersViewModel @Inject constructor(
    private val followRepository: FollowRepository,
    private val repository: Repository,
    private val prefs: MySharedPreferences,
    private val localizationManager: LocalizationManager
) :
    ViewModel() {

    val allFollowers =  MutableSharedFlow<List<FollowersData>>()

    val profileUrl = MutableSharedFlow<Pair<String,Long>>()


    val title = MutableLiveData<String>()


    init {
        title.value = localizationManager.localization(ANALYZE_ALLFOLLOWERS_TITLE)
    }
    suspend fun getFollowData(dataSize: Int) {
        viewModelScope.launch {
            val data = followRepository.getFollowers(dataSize)
            allFollowers.emit(data)
        }

    }

    private suspend fun updateNewProfilePicture(userId:Long, url:String){
        viewModelScope.launch {
            followRepository.updateFollowersNewProfilePicture(userId = userId,url = url)
        }
    }

    suspend fun getUserDetails(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        when(val response = repository.getUserDetails(userId,prefs.allCookie)){
            is Resource.Success->{
                response.data?.let {  itResponse->
                    val ppUrl =  itResponse.user.profilePicUrl
                    val dsUserId = itResponse.user.pk
                    profileUrl.emit(Pair(ppUrl,dsUserId))
                    updateNewProfilePicture(dsUserId,ppUrl)
                }
            }
        }
    }
}