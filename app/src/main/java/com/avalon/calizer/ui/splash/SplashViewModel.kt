package com.avalon.calizer.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    val reelsTray : MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()


}