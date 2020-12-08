package com.avalon.avalon.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.local.CookieDatabase
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: CookieRepository) : ViewModel() {

    val allCookies :LiveData<CookieData> = repository.readAllCookies


    fun addCookie(cookieData: CookieData){
       viewModelScope.launch(Dispatchers.IO) {
          repository.addCookie(cookieData)
       }
    }

}