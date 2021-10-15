package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.MutableLiveData
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



    private val storyViewerList = ArrayList<StoryViewerData>()
    private val followList = ArrayList<FollowersData>()

    val testObserve = MutableLiveData<List<StoryViewerData>>()

    sealed class NotStoryState {
        object Empty : NotStoryState()
        object Loading : NotStoryState()
        object Error : NotStoryState()
        data class StoryList(val storyList: List<StoryData>) : NotStoryState()
        object Success :NotStoryState()
       object Navigate:NotStoryState()

    }



    suspend fun getFollowers() {
        viewModelScope.launch {
            val response = roomRepo.getAllFollowers()
            if (response.isNotEmpty()) {
                followList.addAll(response)
                getStoryId()
            } else {
                _storyViewData.value = NotStoryState.Error
            }
        }
    }

    fun setLoadingState() {
        _storyViewData.value = NotStoryState.Loading
    }

    fun navigateStory(){
        _storyViewData.value = NotStoryState.Navigate
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
                is Resource.Error -> {
                    _storyViewData.value = NotStoryState.Error
                }
            }
        }
    }



     suspend fun getStoryViewer(storyId: String, maxId: String? = null) {
        viewModelScope.launch {
            when (val response = repository.getStoryViewer(
                storyId = storyId,
                cookies= prefs.allCookie,
                maxId = maxId
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
                        getStoryViewer(
                            storyId = itData.updatedMedia.id ,
                             maxId= itNextMaxId)
                        }?: kotlin.run {
                         testObserve.postValue(differenceList())
                            _storyViewData.value = NotStoryState.Success
                        }
                    }
                }
            }
        }
    }

     private fun differenceList():List<StoryViewerData>{
        return storyViewerList.asSequence().filter { storyList->
             followList.none { followList->
                 storyList.pk == followList.dsUserID
             }
         }.toList()
    }


}