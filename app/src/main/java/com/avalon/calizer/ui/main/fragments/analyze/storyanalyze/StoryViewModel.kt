package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    val repository: Repository,
    val prefs: MySharedPreferences
) : ViewModel() {

    init {
        getStoryList()
    }


    private val _storyList = MutableStateFlow<StoryState>(StoryState.Empty)
    val storyList:StateFlow<StoryState> = _storyList

    private val _openStory = MutableSharedFlow<OpenStoryState>()
    val openStory :SharedFlow<OpenStoryState> = _openStory

    val loading = MutableSharedFlow<Boolean>()


    sealed class OpenStoryState{
        data class Success(val storyList:List<String>):OpenStoryState()
        object Error : OpenStoryState()
    }

    sealed class StoryState {
        object Empty : StoryState()
        data class Success(val storyData: List<StoryData>) : StoryState()
        object Error : StoryState()
    }

    fun setLoadingState(isLoading: Boolean) {
        viewModelScope.launch {
            loading.emit(isLoading)
        }
    }

     private fun getStoryList() {
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
                            _storyList.emit(StoryState.Success(storyList))
                        } else {
                            _storyList.emit(StoryState.Error)
                        }
                    } ?: kotlin.run {  _storyList.emit(StoryState.Error) }
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
                            _openStory.emit(OpenStoryState.Success(urlList))
                        } else {
                            _openStory.emit(OpenStoryState.Error)
                        }

                    } ?: kotlin.run {  _openStory.emit(OpenStoryState.Error) }
                }
                is Resource.Error -> {
                    _openStory.emit(OpenStoryState.Error)
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
                            _openStory.emit(OpenStoryState.Success(urlList))
                        } else {
                           _openStory.emit(OpenStoryState.Error)
                        }

                    }?: kotlin.run {  _openStory.emit(OpenStoryState.Error) }
                }
                is Resource.Error -> {
                    _openStory.emit(OpenStoryState.Error)
                }
            }
        }
    }
}