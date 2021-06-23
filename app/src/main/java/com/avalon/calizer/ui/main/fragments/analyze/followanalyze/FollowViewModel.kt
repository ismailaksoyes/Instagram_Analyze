package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avalon.calizer.data.datasource.FollowDataPagingSource
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.accounts.AccountsViewModel
import com.avalon.calizer.ui.main.MainViewModel
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val dbRepository: RoomRepository):ViewModel() {

    private val _allFollow = MutableStateFlow<FollowState>(
        FollowState.Empty)
    val allFollow: StateFlow<FollowState> = _allFollow

    sealed class FollowState{
        object Empty :FollowState()
        object Loading :FollowState()
        data class UpdateItem(val followData:List<FollowData>) :FollowState()
        data class Success(val followData:List<FollowData>) :FollowState()

    }

    suspend fun getFollowData(dataSize:Int){
        delay(1000)
        val data = dbRepository.getFollowersData(dataSize)
        _allFollow.value = FollowState.Success(data)
        _allFollow.value = FollowState.UpdateItem(data)
    }
    suspend fun updateFlow(){
        _allFollow.value = FollowState.Loading
    }
    suspend fun getCookies(userId:Long){
        val userCookie = dbRepository.getAccountCookies(userId)
    }


}