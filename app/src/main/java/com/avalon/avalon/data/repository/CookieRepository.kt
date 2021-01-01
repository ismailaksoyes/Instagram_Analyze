package com.avalon.avalon.data.repository

import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.local.FollowersData
import com.avalon.avalon.data.local.FollowingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CookieRepository(private val cookieDao: CookieDao) {



    suspend fun addCookie(cookieData: CookieData){
      cookieDao.addCookie(cookieData)
    }

     suspend fun getCookies():CookieData{
        return cookieDao.readAllData()
    }

    suspend fun addFollowers(followersData:FollowersData ){
        CoroutineScope(Dispatchers.IO).launch {
            cookieDao.addFollowers(followersData)
        }

    }
    suspend fun addFollowing(followingData: FollowingData){
        cookieDao.addFollowing(followingData)
    }
    suspend fun getFollowers(): List<FollowersData> {
        return cookieDao.getFollowers()
    }
    suspend fun getFollowing():List<FollowingData>{
        return cookieDao.getFollowing()
    }

}