package com.avalon.calizer.ui.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.weblogin.CookiesData
import com.avalon.calizer.data.remote.insresponse.ApiResponseReelsTray
import com.avalon.calizer.data.remote.insresponse.ApiResponseUserDetails
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.main.fragments.profile.ProfileViewModel
import com.avalon.calizer.utils.Resource
import com.avalon.calizer.utils.toAccountsInfoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val repository: Repository
) : ViewModel() {

    private val _allAccounts = MutableStateFlow<LastAccountsState>(LastAccountsState.Empty)
    val allAccounts: StateFlow<LastAccountsState> = _allAccounts

    private val _cookieData = MutableStateFlow<AccountCookieState>(AccountCookieState.Empty)
    val cookieData: StateFlow<AccountCookieState> = _cookieData

    sealed class LastAccountsState {
        object Empty : LastAccountsState()
        data class Success(var allAccounts: List<AccountsData>) : LastAccountsState()
        object Loading : LastAccountsState()
        data class OldData(var allAccounts: List<AccountsData>) : LastAccountsState()
        data class UserDetails(var userDetails: ApiResponseUserDetails) : LastAccountsState()
        object UpdateData : LastAccountsState()
        data class Error(val error: String) : LastAccountsState()

    }

    sealed class UserDetailsState {
        object Empty : UserDetailsState()
        object Loading : UserDetailsState()
    }

    sealed class AccountCookieState {
        object Empty : AccountCookieState()
        data class Cookies(val cookies: String) : AccountCookieState()
        data class SplitCookie(val cookiesData: CookiesData) : AccountCookieState()
        data class CookieValid(val cookies: String) : AccountCookieState()
        object Success : AccountCookieState()

    }

    private val _reelsTray = MutableLiveData<Resource<ApiResponseReelsTray>>()
    val reelsTray: LiveData<Resource<ApiResponseReelsTray>>
        get() = _reelsTray

    private val _userDetails = MutableLiveData<Resource<ApiResponseUserDetails>>()
    val userDetails: LiveData<Resource<ApiResponseUserDetails>>
        get() = _userDetails


    suspend fun getReelsTray(cookies: String) {
        when (val response = repository.getReelsTray(cookies)) {
            is Resource.Success -> {
                response.data?.let { itData ->
                    _cookieData.value = AccountCookieState.CookieValid(cookies)
                }
            }
        }
    }

    suspend fun getUserDetails(cookies: String, userId: Long) {
        when (val response = repository.getUserDetails(userId, cookies)) {
            is Resource.Success -> {
                response.data?.let { itData ->
                    itData.user.followerCount?.let {
                        _allAccounts.value = LastAccountsState.UserDetails(itData)
                    } ?: kotlin.run {
                        deleteAccount()
                        _allAccounts.value = LastAccountsState.Error("")
                    }
                }

            }
            is Resource.Error -> {
                deleteAccount()
                _allAccounts.value = LastAccountsState.Error("")
            }
        }
    }

    suspend fun getAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            _allAccounts.value = LastAccountsState.Loading
            val data = roomRepository.getAccounts()
            if (data.isEmpty()) {
                _allAccounts.value = LastAccountsState.Empty
            } else {
                _allAccounts.value = LastAccountsState.OldData(data)
            }

        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            roomRepository.deleteAccount()
            getAccountList()
        }
    }

    suspend fun getLastAccountList() {
        viewModelScope.launch(Dispatchers.IO) {
            _allAccounts.value = LastAccountsState.Success(roomRepository.getAccounts())
        }
    }

    fun setCookies(cookies: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _cookieData.value = AccountCookieState.Cookies(cookies)
        }
    }

    fun setSplitCookies(cookiesData: CookiesData) {
        viewModelScope.launch(Dispatchers.IO) {
            _cookieData.value = AccountCookieState.SplitCookie(cookiesData)
        }
    }


    suspend fun addAccount(accountsData: AccountsData) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addAccount(accountsData)
            _cookieData.value = AccountCookieState.Success
        }

    }

    suspend fun updateAccount(
        profilePicture: String?,
        user_name: String?,
        dsUserId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.updateAccount(profilePicture, user_name, dsUserId)
            _allAccounts.value = LastAccountsState.UpdateData
        }

    }
}