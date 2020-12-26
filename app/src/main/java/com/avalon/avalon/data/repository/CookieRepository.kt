package com.avalon.avalon.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieData
import retrofit2.Response

class CookieRepository(private val cookieDao: CookieDao) {



    suspend fun addCookie(cookieData: CookieData){
      cookieDao.addCookie(cookieData)
    }

     suspend fun getCookies():CookieData{
        return cookieDao.readAllData()
    }

}