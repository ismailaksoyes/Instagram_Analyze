package com.avalon.avalon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.repository.CookieRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory (private val repository: CookieRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }

}