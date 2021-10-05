package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.data.local.story.StoryViewerData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotFollowStoryViewsViewModel @Inject constructor(
    val roomRepo: RoomRepository,
    val prefs: MySharedPreferences,
    val repository: Repository
) : ViewModel() {
    private val _storyViewData = MutableStateFlow<NotStoryState>(NotStoryState.Empty)
    val storyViewData: StateFlow<NotStoryState> = _storyViewData

    private val _followersData = MutableStateFlow<FollowersState>(FollowersState.Empty)
    val followersData:StateFlow<FollowersState> = _followersData

    private val storyViewerList = ArrayList<StoryViewerData>()

    sealed class NotStoryState {
        object Empty : NotStoryState()
        object Loading : NotStoryState()
        object Error : NotStoryState()
        data class StoryList(val storyList: List<StoryData>) : NotStoryState()
        data class StoryViewerSync(val storyViewer:List<StoryViewerData>):NotStoryState()
        data class StoryViewerList(val storyViewer:List<StoryViewerData>):NotStoryState()

    }

    sealed class FollowersState{
        object Empty : FollowersState()
        object Loading : FollowersState()
        object Error : FollowersState()
        data class FollowersList(val followersData: List<FollowersData>) : FollowersState()
    }

    suspend fun getFollowers() {
        viewModelScope.launch {
            val response = roomRepo.getAllFollowers()
            if (response.isNotEmpty()) {
                _followersData.value = FollowersState.FollowersList(response)
            } else {
                _followersData.value = FollowersState.Error
            }
        }
    }

    fun setLoadingState() {
        _storyViewData.value = NotStoryState.Loading
    }

    suspend fun getStoryId() {
        viewModelScope.launch {
            when (val response = repository.getStory(prefs.selectedAccount, prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.reel?.items?.let { itItems ->
                        val storyDataList = ArrayList<StoryData>()
                        itItems.forEach { itItemsFor ->

                            itItemsFor.imageVersions2?.let { itImage ->
                                storyDataList.add(
                                    StoryData(
                                        imageUrl = itImage.candidates[0].url,
                                        storyId = itItemsFor.id
                                    )
                                )
                            }
                        }
                        if (storyDataList.isNotEmpty()) {
                            _storyViewData.value = NotStoryState.StoryList(storyDataList)
                        } else {
                            _storyViewData.value = NotStoryState.Error
                        }

                    } ?: kotlin.run {
                        _storyViewData.value = NotStoryState.Error
                    }
                }
                is Resource.DataError -> {
                    _storyViewData.value = NotStoryState.Error
                }
            }
        }
    }



     suspend fun getStoryViewer(storyId: String, maxId: String? = null) {
        viewModelScope.launch {
            when (val response = repository.getStoryViewer(
                cookies = prefs.allCookie,
                maxId = maxId,
                storyId = storyId
            )) {
                is Resource.Success -> {
                    response.data?.let { itData ->
                        itData.users.forEach { itUser ->
                            storyViewerList.add(
                                StoryViewerData(
                                    pk = itUser.pk.toLong(),
                                    userName = itUser.username,
                                    profileUrl = itUser.profilePicUrl
                                )
                            )
                        }
                        itData.nextMaxId?.let { itNextMaxId ->
                         getStoryViewer(itData.updatedMedia.id,itNextMaxId)
                        }?: kotlin.run {
                            _storyViewData.value = NotStoryState.StoryViewerList(storyViewerList)
                        }
                    }
                }
            }
        }
    }


}