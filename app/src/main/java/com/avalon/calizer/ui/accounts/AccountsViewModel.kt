package com.avalon.calizer.ui.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountsViewModel @ViewModelInject constructor(
    private val roomRepository: RoomRepository,
    private val repository: Repository
) : ViewModel() {
    val allAccounts: MutableLiveData<List<AccountsData>> = MutableLiveData()

    private val _reelsTray = MutableLiveData<Resource<ApiResponseReelsTray>>()
    val reelsTray :LiveData<Resource<ApiResponseReelsTray>>
    get() = _reelsTray

    private val _userDetails = MutableLiveData<Resource<ApiResponseUserDetails>>()
    val userDetails:LiveData<Resource<ApiResponseUserDetails>>
    get() = _userDetails


     fun getReelsTray(cookies:String) = viewModelScope.launch {
        _reelsTray.postValue(Resource.loading(null))
        repository.getReelsTray(cookies).let {
            if (it.isSuccessful){
                _reelsTray.postValue(Resource.success(it.body()))
            }else{
                _reelsTray.postValue(Resource.error(it.errorBody().toString(),null))
            }
        }

    }

    fun getUserDetails(cookies: String,userId:String) = viewModelScope.launch {
        _userDetails.postValue(Resource.loading(null))
        repository.getUserDetails(cookies,userId).let {
            if (it.isSuccessful){
                _userDetails.postValue(Resource.success(it.body()))
            }else{
                _userDetails.postValue(Resource.error(it.errorBody().toString(),null))
            }
        }
    }

    fun getAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            allAccounts.postValue(roomRepository.getAccounts())
        }
    }

    suspend fun addAccount(accountsData: AccountsData) {
        roomRepository.addAccount(accountsData)
    }
}