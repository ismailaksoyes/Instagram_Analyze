package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import androidx.lifecycle.ViewModel
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        object Error : StoryState()
    }

    suspend fun getStoryList() {
        repository.getReelsTray(prefs.allCookie).let { itStoryData ->
            if (itStoryData.isSuccessful) {
                itStoryData.body()?.tray?.let { itTray ->
                    val storyList = ArrayList<StoryData>()
                    itTray.forEach { itFor ->
                        storyList.add(
                            StoryData(
                                itFor.user.profilePicUrl,
                                itFor.user.pk,
                                itFor.hasVideo
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