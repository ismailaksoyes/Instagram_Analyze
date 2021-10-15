package com.avalon.calizer.ui.main.fragments.profile

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.local.profile.FollowersCount
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import com.avalon.calizer.utils.toAccountsInfoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


fun <R, A> Fragment.observeTest(liveData: MutableLiveData<A>, block: (A) -> R) {
    liveData.observe(viewLifecycleOwner, Observer {
        block.invoke(it)
    })
}


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: MySharedPreferences,
    private val repository: Repository,
    private val roomDb: RoomRepository
) : ViewModel() {

    private val _userData = MutableStateFlow<UserDataFlow>(UserDataFlow.Empty)
    val userData: StateFlow<UserDataFlow> = _userData

    val userModel: MutableLiveData<AccountsInfoData> = MutableLiveData<AccountsInfoData>()

    val followersCount: MutableLiveData<FollowersCount> = MutableLiveData<FollowersCount>()

    val testLiveData: MutableLiveData<String> = MutableLiveData<String>()


    sealed class UserDataFlow {
        object Empty : UserDataFlow()
        object Error : UserDataFlow()
        object Loading : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) : UserDataFlow()

    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG12312312", "onCleared: ")
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
            when (val response =
                repository.getUserDetails(prefs.selectedAccount, prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.let { itData ->
                        itData.user.followerCount?.let {
                            _userData.value =
                                UserDataFlow.GetUserDetails(itData.toAccountsInfoData())
                            testLiveData.postValue("hakkirecep")
                        } ?: kotlin.run {
                            _userData.value = UserDataFlow.Error
                        }

                    }
                }
                is Resource.Error -> {
                    _userData.value = UserDataFlow.Error
                    val errorCode = response.errorCode

                }
            }

        }
    }

   suspend fun getFollowersCount() {
        viewModelScope.launch {
            followersCount.postValue(
                FollowersCount(
                    roomDb.getNewFollowersCount().toString(),
                    roomDb.getUnFollowersCount().toString(),
                    getEarnedFollowersPercentage().toString().plus("%"),
                    getLostFollowersPercentage().toString().plus("%")
                )
            )
        }
    }


    suspend fun getEarnedFollowersCount(): Long {
        return roomDb.getFollowersCount() - roomDb.getOldFollowersCount()
    }

    private suspend fun getEarnedFollowersPercentage(): Long {
        val oldFollowerCount = roomDb.getOldFollowersCount()
        if (oldFollowerCount == 0L) return 0L
        return ((roomDb.getFollowersCount() - oldFollowerCount) / oldFollowerCount) * 100L
    }

    private suspend fun getLostFollowersPercentage(): Long {
        val followersCount = roomDb.getFollowersCount()
        if (roomDb.getUnFollowersCount() == 0L) return 0L
        return ((followersCount - roomDb.getUnFollowersCount()) / followersCount) * 100L
    }
}