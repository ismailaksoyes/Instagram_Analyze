package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val dbRepository: RoomRepository):ViewModel() {
    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData: StateFlow<UserDataFlow> = _userData

    sealed class UserDataFlow {
        object Empty : UserDataFlow()
        data class GetFollowData(var accountsData:List<FollowData>) : UserDataFlow()

    }

    fun followData(){
        viewModelScope.launch {
            _userData.value = UserDataFlow.GetFollowData(dbRepository.getFollowersData())
        }
    }

}