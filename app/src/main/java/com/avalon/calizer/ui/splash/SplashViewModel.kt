package com.avalon.calizer.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: Repository,private val localizationManager: LocalizationManager):ViewModel() {
    val reelsTray : MutableLiveData<Response<ApiResponseReelsTray>> = MutableLiveData()

    val localHash = MutableSharedFlow<HashMap<String, String>> ()


    fun getLocalization(lang:String){
        viewModelScope.launch {
            when(val response = repository.getTranslation(lang)){
                is Resource.Success->{
                    response.data?.let { itLocalization->
                        localizationManager.setLocalization(itLocalization)
                        localHash.emit(itLocalization)
                    }

                }
            }
        }
    }

}