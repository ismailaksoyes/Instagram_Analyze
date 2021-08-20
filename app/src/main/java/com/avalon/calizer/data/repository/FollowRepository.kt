package com.avalon.calizer.data.repository

import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FollowRepository @Inject constructor(private val roomDao: RoomDao,private val prefs: MySharedPreferences) {



 suspend fun getUserCookie():AccountsData{
    return roomDao.getUserCookies(prefs.selectedAccount)
 }

    suspend fun getFollowersData(position:Int):List<FollowData>{
        return roomDao.getFollowersData(position)
    }

    suspend fun getNoFollowersData(userId:Long,position: Int):List<FollowData>{
        return roomDao.getUnFollowers(userId,position)
    }

}