package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            when(val response = repository.getUserPk(username)){

                is Resource.Success->{
                    response.data?.let { itUserPk->
                        val userpk =  itUserPk.graphql.user.id
                        _userPk.value = UserPkState.Success(userpk.toLong())
                        Log.d("ResponseStory","$userpk")
                    }
                }
                is Resource.DataError->{
                    //no such user
                    _userPk.value = UserPkState.Error
                    Log.d("ResponseStory","${response.errorCode}")
                }
            }
        }

    }
    fun setLoadingPk(){
        _userPk.value = UserPkState.Loading
    }

}