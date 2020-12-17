package com.avalon.avalon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import java.lang.IllegalArgumentException

class LoginViewModelFactory (private val dbRepository: CookieRepository,private val repository: Repository ):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(dbRepository,repository) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }

}