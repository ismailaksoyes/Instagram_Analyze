package com.avalon.calizer.ui.main.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val dbRepository: RoomRepository) : ViewModel() {

    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData : StateFlow<UserDataFlow> = _userData

    val userDetailsData = MutableLiveData<AccountsInfoData>()

    private val _userModel  = MutableStateFlow(AccountsInfoData())
    val userModel : StateFlow<AccountsInfoData> = _userModel


    sealed class UserDataFlow{
        object Empty : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) :UserDataFlow()

    }


    fun getUserDetails(userId: Long){
        viewModelScope.launch {
            _userModel.emit(dbRepository.getUserInfo(userId))

        }
    }

}