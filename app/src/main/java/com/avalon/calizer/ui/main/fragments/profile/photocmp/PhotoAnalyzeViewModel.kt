package com.avalon.calizer.ui.main.fragments.profile.photocmp

import androidx.lifecycle.ViewModel
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoAnalyzeViewModel @Inject constructor() : ViewModel() {

    private val _photoAnalyzeData = MutableStateFlow<PhotoState>(PhotoState.Empty)
    val photoAnalyzeData : StateFlow<PhotoState> = _photoAnalyzeData


    sealed class PhotoState {
        object Empty : PhotoState()
        object Loading : PhotoState()
        data class Random(val data: Int):PhotoState()
        data class Success(val photoData:List<PhotoAnalyzeData>):PhotoState()
    }

    fun setPhotoData(photoData: List<PhotoAnalyzeData>){
        _photoAnalyzeData.value = PhotoState.Success(photoData)
    }

    fun randomData(data:Int){
        _photoAnalyzeData.value = PhotoState.Random(data)
    }

}