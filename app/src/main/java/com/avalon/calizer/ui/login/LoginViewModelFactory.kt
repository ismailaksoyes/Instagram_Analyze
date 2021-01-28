package com.avalon.calizer.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.data.repository.Repository
import java.lang.IllegalArgumentException

class LoginViewModelFactory (private val dbRepository: RoomRepository, private val repository: Repository ):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(dbRepository,repository) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }

}