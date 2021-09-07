package com.avalon.calizer.data.repository

import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.utils.MySharedPreferences
import javax.inject.Inject

class FollowRepository @Inject constructor(private val roomDao: RoomDao,private val prefs: MySharedPreferences) {



 suspend fun getUserCookie():AccountsData{
    return roomDao.getUserCookies(prefs.selectedAccount)
 }

    suspend fun getFollowersData(position:Int):List<FollowersData>{
        return roomDao.getFollowersData(position,prefs.selectedAccount)
    }

    suspend fun getNoFollowersData(userId:Long,position: Int):List<FollowersData>{
        return roomDao.getUnFollowers(userId,position)
    }

}