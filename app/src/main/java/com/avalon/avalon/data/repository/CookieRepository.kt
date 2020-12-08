package com.avalon.avalon.data.repository

import androidx.lifecycle.LiveData
import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieData
import retrofit2.Response

class CookieRepository(private val cookieDao: CookieDao) {

    val readAllCookies = cookieDao.readAllData()
    suspend fun addCookie(cookieData: CookieData){
      cookieDao.addCookie(cookieData)
    }


}