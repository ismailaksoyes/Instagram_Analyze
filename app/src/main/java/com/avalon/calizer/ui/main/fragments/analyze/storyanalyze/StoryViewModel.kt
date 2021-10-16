package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    val repository: Repository,
    val prefs: MySharedPreferences
) : ViewModel() {

    private val _storyData = MutableStateFlow<StoryState>(StoryState.Empty)
    val storyData: StateFlow<StoryState> = _storyData


    sealed class StoryState {
        object Empty : StoryState()
        data class Success(val storyData: List<StoryData>) : StoryState()
        data class ClickItem(val userId: Long) : StoryState()
        data class OpenStory(val urlList: List<String>) : StoryState()
        data class OpenHg(val urlList: List<String>) : StoryState()
        data class Loading(val isLoading: Boolean) : StoryState()
        object Error : StoryState()
    }


    fun setClickItemId(userId: Long) {
        _storyData.value = StoryState.ClickItem(userId)
    }

    fun setLoadingState(isLoading: Boolean) {
        _storyData.value = StoryState.Loading(isLoading)

    }

    suspend fun getStoryList() {
        viewModelScope.launch {
            when (val response = repository.getReelsTray(prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.tray?.let { itTray ->
                        val storyList = ArrayList<StoryData>()
                        itTray.forEach { itFor ->
                            storyList.add(
                                StoryData(
                                    imageUrl = itFor.user.profilePicUrl,
                                    pk = itFor.user.pk,
                                    hasVideo = itFor.hasVideo,
                                    storyId = null
                                )
                            )
                        }
                        if (storyList.size > 0) {
                            _storyData.value = StoryState.Success(storyList)
                        } else {
                            _storyData.value = StoryState.Error
                        }
                    } ?: kotlin.run { _storyData.value = StoryState.Error }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


    suspend fun getStory(userId: Long) {
        viewModelScope.launch {
            when (val response = repository.getStory(userId, prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.reel?.let { itReel ->
                        val urlList = ArrayList<String>()
                        itReel.items.forEach { itItems ->
                            itItems.videoVersions?.let { itVideo ->
                                urlList.add(itVideo[0].url)
                            } ?: kotlin.run {
                                itItems.imageVersions2?.let { itImage ->
                                    urlList.add(itImage.candidates[0].url)
                                }
                            }
                        }
                        if (urlList.isNotEmpty()) {
                            _storyData.value = StoryState.OpenStory(urlList)
                        } else {
                            _storyData.value = StoryState.Error
                        }


                    } ?: kotlin.run { _storyData.value = StoryState.Error }
                }
                is Resource.Error -> {
                    _storyData.value = StoryState.Error
                    val errorcode = response.errorCode
                }
            }

        }
    }

    suspend fun getHighlightsStory(userHg: String) {
        viewModelScope.launch {

            when (val response = repository.getHighlightsStory(userHg, prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.reels?.map { itData ->
                        val urlList = ArrayList<String>()
                        itData.value.items.forEach { itItems ->
                            itItems.videoVersions?.let { itVideo ->
                                urlList.add(itVideo[0].url)
                            } ?: kotlin.run {
                                itItems.imageVersions2?.let { itImage ->
                                    urlList.add(itImage.candidates[0].url)
                                }
                            }
                        }
                        if (urlList.isNotEmpty()) {
                            _storyData.value = StoryState.OpenStory(urlList)
                        } else {
                            _storyData.value = StoryState.Error
                        }

                    }?: kotlin.run { _storyData.value = StoryState.Error }
                }
                is Resource.Error -> {
                    _storyData.value = StoryState.Error
                    val errorcode = response.errorCode
                }
            }
        }
    }
}