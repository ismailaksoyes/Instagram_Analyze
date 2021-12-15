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
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import com.avalon.calizer.utils.toAccountsInfoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject





@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: MySharedPreferences,
    private val repository: Repository,
    private val roomDb: RoomRepository,
    private val localizationManager: LocalizationManager
) : ViewModel() {

    private val _userData = MutableSharedFlow<UserDataFlow>()
    val userData: SharedFlow<UserDataFlow> = _userData

    val userModel: MutableLiveData<AccountsInfoData> = MutableLiveData<AccountsInfoData>()

    val followersCount: MutableLiveData<FollowersCount> = MutableLiveData<FollowersCount>()

    val poseScore:MutableLiveData<Int> = MutableLiveData()

    val faceScore:MutableLiveData<Int> = MutableLiveData()

    val poseScore2:MutableSharedFlow<Int> = MutableSharedFlow()



    sealed class UserDataFlow {
        object Empty : UserDataFlow()
        object Error : UserDataFlow()
        object Loading : UserDataFlow()
        data class GetUserDetails(var accountsInfoData: AccountsInfoData) : UserDataFlow()

    }



    fun setViewUserData(accountsInfoData: AccountsInfoData) {
        userModel.value = accountsInfoData
    }

    fun setFaceScore(score:Int){
        faceScore.postValue(score)
        viewModelScope.launch {
            poseScore2.emit(score)
        }

    }

    fun setPoseScore(score: Int){
        poseScore.postValue(score)
    }


    fun setUserDetailsLoading() {
        viewModelScope.launch {
            _userData.emit(UserDataFlow.Loading)
        }
    }


    suspend fun getUserDetails() {
        viewModelScope.launch {
            when (val response =
                repository.getUserDetails(prefs.selectedAccount, prefs.allCookie)) {
                is Resource.Success -> {
                    response.data?.let { itData ->
                        itData.user.followerCount?.let {
                            _userData.emit(UserDataFlow.GetUserDetails(itData.toAccountsInfoData()))

                        } ?: kotlin.run {
                            _userData.emit(UserDataFlow.Error)
                        }

                    }
                }
                is Resource.Error -> {
                    _userData.emit(UserDataFlow.Error)
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
                    if (getEarnedFollowersPercentage() == 0f) {
                        "%0"
                    } else {
                        "%" + getEarnedFollowersPercentage().toString().substring(0, 3)
                    },
                    if (getLostFollowersPercentage() == 0f) {
                        "%0"
                    } else {
                        "%" + getLostFollowersPercentage().toString().substring(0, 3)
                    }
                        )
            )
        }
    }


    private suspend fun getEarnedFollowersPercentage(): Float {
        val oldFollowerCount = roomDb.getOldFollowersCount().toFloat()
        val followersCount = roomDb.getNewFollowersCount().toFloat()
        if (oldFollowerCount == 0f) return 0f
        if (followersCount == 0f) return 0f
        return (((followersCount - (followersCount - oldFollowerCount)) / followersCount) * 100)
    }

    private suspend fun getLostFollowersPercentage(): Float {
        val followersCount = roomDb.getFollowersCount().toFloat()
        val unFollowersCount = roomDb.getUnFollowersCount().toFloat()
        if (followersCount == 0f) return 0f
        if (unFollowersCount == 0f) return 0f
        return (((followersCount - (followersCount - unFollowersCount)) / followersCount) * 100)
    }
}