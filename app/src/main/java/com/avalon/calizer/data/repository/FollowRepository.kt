package com.avalon.calizer.data.repository

import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.utils.MySharedPreferences
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val roomDao: RoomDao,
    private val prefs: MySharedPreferences
) {


     fun getUserCookie(): String? {
        return prefs.allCookie
    }

    suspend fun getFollowers(position: Int): List<FollowersData> {
        return roomDao.getFollowersData(position, prefs.selectedAccount)
    }

    suspend fun getFollowing(position: Int):List<FollowingData>{
        return roomDao.getFollowingData(position,prefs.selectedAccount)
    }

    suspend fun getUnFollowers(position: Int): List<FollowersData> {
        return roomDao.getUnFollowers(prefs.selectedAccount, position)
    }

    suspend fun getUnFollowersCount():Long{
        return roomDao.getUnFollowersCount(prefs.selectedAccount)
    }

    suspend fun getNotFollowers(position: Int): List<FollowersData> {
        return roomDao.getNotFollowers(prefs.selectedAccount, position)
    }

    suspend fun getNewFollowers(position: Int):List<FollowersData>{
        return roomDao.getNewFollowers(prefs.selectedAccount,position)
    }
    suspend fun getNewFollowersCount():Long{
        return roomDao.getNewFollowersCount(prefs.selectedAccount)
    }

    suspend fun updateFollowersNewProfilePicture(userId:Long,url:String){
        roomDao.updateFollowersNewProfilePicture(userId,url)
    }

    suspend fun updateFollowingNewProfilePicture(userId:Long,url:String){
        roomDao.updateFollowingNewProfilePicture(userId,url)
    }

}