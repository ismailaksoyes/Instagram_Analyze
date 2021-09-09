package com.avalon.calizer.ui.main.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.main.MainViewModel
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.toAccountsInfoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val prefs: MySharedPreferences,
    private val repository: Repository
) : ViewModel() {

    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData: StateFlow<UserDataFlow> = _userData

    val userModel: MutableLiveData<AccountsInfoData> = MutableLiveData<AccountsInfoData>()

    private val _navigateFlow = MutableStateFlow<NavigateFlow>(NavigateFlow.Empty)
    val navigateFlow: StateFlow<NavigateFlow> = _navigateFlow


    sealed class UserDataFlow {
        object Empty : UserDataFlow()
        object Loading : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) : UserDataFlow()

    }

    sealed class NavigateFlow {
        object Empty : NavigateFlow()
        object PhotoAnalyze : NavigateFlow()
        object AccountsPage : NavigateFlow()
    }

    fun navigateProfileToPhotoAnalyze() {
        _navigateFlow.value = NavigateFlow.PhotoAnalyze
    }

    fun navigateProfileToAccountsPage() {
        _navigateFlow.value = NavigateFlow.AccountsPage
    }

    fun setViewUserData(accountsInfoData: AccountsInfoData) {
        userModel.value = accountsInfoData
    }

    fun setUserDetailsLoading() {
        viewModelScope.launch {
            _userData.value = UserDataFlow.Loading
        }
    }


    suspend fun getUserDetails() {
        viewModelScope.launch {
            repository.getUserDetails(prefs.selectedAccount, prefs.allCookie).let { itUserDetails ->
                if (itUserDetails.isSuccessful) {
                    itUserDetails.body()?.let { itBody ->
                        val accountsInfoData = itBody.toAccountsInfoData()
                        _userData.value = UserDataFlow.GetUserDetails(accountsInfoData)
                    }

                }
            }

        }
    }

}