package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.error.ErrorMapper
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryViewModel @Inject constructor(
    val repository: Repository,
    val prefs: MySharedPreferences
) : ViewModel() {

    private val _storyData = MutableStateFlow<StoryState>(StoryState.Empty)
    val storyData: StateFlow<StoryState> = _storyData


    sealed class StoryState {
        object Empty : StoryState()
        data class Success(val storyData: List<StoryData>) : StoryState()
        data class ClickItem(val userId: Long):StoryState()
        data class OpenStory(val urlList: List<String>) : StoryState()
        object Error : StoryState()
    }

    fun setClickItemId(userId: Long){
        _storyData.value = StoryState.ClickItem(userId)
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
                                    itFor.user.profilePicUrl, itFor.user.pk, itFor.hasVideo
                                )
                            )
                        }
                        if (storyList.size > 0) {
                            _storyData.value = StoryState.Success(storyList)
                        } else {
                            _storyData.value = StoryState.Error
                        }
                    }
                }
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
    suspend fun getUserPk(username:String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repository.getUserPk(username)){

            }
        }

    }

    suspend fun getStory(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
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
                        _storyData.value = StoryState.OpenStory(urlList)

                    }
                }
                is Resource.DataError -> {
                    val errorcode = response.errorCode
                }
            }

        }
    }
}