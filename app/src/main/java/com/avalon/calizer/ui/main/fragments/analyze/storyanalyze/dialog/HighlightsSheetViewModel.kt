package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.story.HighlightsData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HighlightsSheetViewModel @Inject constructor(
    val repository: Repository,
    val prefs: MySharedPreferences
) : ViewModel() {

    private val _userPkHigh = MutableStateFlow<UserPkState>(UserPkState.Empty)
    val userPkHigh: StateFlow<UserPkState> = _userPkHigh

    sealed class UserPkState {
        object Empty : UserPkState()
        data class Success(val userId: Long) : UserPkState()
        data class Highlights(val highlightsData: List<HighlightsData>):UserPkState()
        data class ClickItem(val highlightsId:String):UserPkState()
        object Loading : UserPkState()
        object Error : UserPkState()

    }

    fun setClickItemId(userId: String){
        _userPkHigh.value = UserPkState.ClickItem(userId)
    }

    suspend fun getHighlights(userId: Long){
        viewModelScope.launch {
            when(val response = repository.getHighlights(userId,prefs.allCookie)){
                is Resource.Success->{
                    val highlightsDataList = ArrayList<HighlightsData>()
                    response.data?.tray?.let { itTray->
                        itTray.forEach { itItem->
                            highlightsDataList.add(HighlightsData(imageUrl = itItem.coverMedia.croppedImageVersion.url,itItem.id))
                        }
                    }
                    if (highlightsDataList.isNotEmpty()){
                        _userPkHigh.value = UserPkState.Highlights(highlightsDataList)
                    }else{
                        _userPkHigh.value = UserPkState.Error
                    }
                }
                is Resource.Error ->{
                    _userPkHigh.value = UserPkState.Error
                }
            }
        }
    }

    suspend fun getUserPk(username: String) {

        viewModelScope.launch {
            when (val response = repository.getUserPk(username, prefs.allCookie)) {

                is Resource.Success -> {
                    response.data?.user?.let { itUser ->
                        val userPk = itUser.pk
                        _userPkHigh.value = UserPkState.Success(userPk.toLong())
                    } ?: kotlin.run {
                        _userPkHigh.value = UserPkState.Error
                    }

                }
                is Resource.Error -> {
                    _userPkHigh.value = UserPkState.Error
                }
            }
        }

    }

    fun setLoadingPk() {
        _userPkHigh.value = UserPkState.Loading
    }
}