package com.avalon.calizer.ui.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val repository: Repository
) : ViewModel() {
    //val allAccounts: MutableLiveData<List<AccountsData>> = MutableLiveData()

    private val _allAccounts = MutableStateFlow<LastAccountsState>(LastAccountsState.Empty)
    val allAccounts: StateFlow<LastAccountsState> = _allAccounts

    sealed class LastAccountsState {
        object Empty : LastAccountsState()
        data class Success(var allAccounts: List<AccountsData>) : LastAccountsState()
        object Loading : LastAccountsState()
        data class OldData(var allAccounts: List<AccountsData>) : LastAccountsState()
        data class UserDetails(var userDetails: Resource<ApiResponseUserDetails>) : LastAccountsState()
        object UpdateData : LastAccountsState()
        data class Error(val error: String) : LastAccountsState()


    }

    private val _reelsTray = MutableLiveData<Resource<ApiResponseReelsTray>>()
    val reelsTray: LiveData<Resource<ApiResponseReelsTray>>
        get() = _reelsTray

    private val _userDetails = MutableLiveData<Resource<ApiResponseUserDetails>>()
    val userDetails: LiveData<Resource<ApiResponseUserDetails>>
        get() = _userDetails


    fun getReelsTray(cookies: String) = viewModelScope.launch {
        _reelsTray.postValue(Resource.loading(null))
        repository.getReelsTray(cookies).let {
            if (it.isSuccessful) {
                _reelsTray.postValue(Resource.success(it.body()))
            } else {
                _reelsTray.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }

    }

    fun setAccountInfo(accountsInfoData: AccountsInfoData){
        viewModelScope.launch {
            roomRepository.addAccountInfo(accountsInfoData)
        }
    }

    fun getUserDetails(cookies: String, userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.getUserDetails(cookies, userId).let {
            if (it.isSuccessful) {
                _allAccounts.value = LastAccountsState.UserDetails(Resource.success(it.body()))
             //   _allAccounts.value = LastAccountsState.UpdateData
            } else {
                _allAccounts.value =
                    LastAccountsState.UserDetails(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun getAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            _allAccounts.value = LastAccountsState.Loading
            val data = roomRepository.getAccounts()
            if (data.isEmpty()) {
                _allAccounts.value = LastAccountsState.Empty
            } else {

                _allAccounts.value = LastAccountsState.OldData(roomRepository.getAccounts())
            }

        }
    }

    fun getLastAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            _allAccounts.value = LastAccountsState.Success(roomRepository.getAccounts())
        }
    }


    suspend fun addAccount(accountsData: AccountsData) {
        roomRepository.addAccount(accountsData)
    }

    suspend fun updateAccount(
        profilePicture: String?,
        user_name: String?,
        dsUserId: String?
    ) {
        roomRepository.updateAccount(profilePicture , user_name, dsUserId)
        delay(1000L)
        _allAccounts.value = LastAccountsState.UpdateData
    }
}