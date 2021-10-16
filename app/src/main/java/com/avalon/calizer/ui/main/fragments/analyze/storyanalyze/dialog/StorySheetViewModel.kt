package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorySheetViewModel@Inject constructor(
    val repository: Repository,
    val prefs: MySharedPreferences
) : ViewModel() {

    private val _userPk = MutableStateFlow<UserPkState>(UserPkState.Empty)
    val userPk : StateFlow<UserPkState> = _userPk

    sealed class UserPkState{
        object Empty:UserPkState()
        data class Success(val userId: Long):UserPkState()
        object Loading:UserPkState()
        object Error:UserPkState()

    }

    suspend fun getUserPk(username:String){

        viewModelScope.launch {
            when(val response = repository.getUserPk(username,prefs.allCookie)){

                is Resource.Success->{
                    response.data?.user?.let { itUser->
                        val userPk =  itUser.pk
                        _userPk.value = UserPkState.Success(userPk.toLong())
                    }?: kotlin.run {
                        _userPk.value = UserPkState.Error
                    }

                }
                is Resource.Error ->{
                    //no such user
                    _userPk.value = UserPkState.Error
                }
            }
        }

    }
    fun setLoadingPk(){
        _userPk.value = UserPkState.Loading
    }

}