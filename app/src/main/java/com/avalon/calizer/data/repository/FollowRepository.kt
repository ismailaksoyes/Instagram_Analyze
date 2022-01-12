package com.avalon.calizer.data.repository

import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.data.local.analyze.PostUserData
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

    suspend fun getFollowers(): List<FollowersData> {
        return roomDao.getFollowersData(prefs.selectedAccount)
    }

    suspend fun getFollowing():List<FollowingData>{
        return roomDao.getFollowingData(prefs.selectedAccount)
    }

    suspend fun getUnFollowers(): List<FollowersData> {
        return roomDao.getUnFollowers(prefs.selectedAccount)
    }

    suspend fun getUnFollowersCount():Long{
        return roomDao.getUnFollowersCount(prefs.selectedAccount)
    }

    suspend fun getNotFollowers(): List<FollowersData> {
        return roomDao.getNotFollowers(prefs.selectedAccount)
    }

    suspend fun getNewFollowers():List<FollowersData>{
        return roomDao.getNewFollowers(prefs.selectedAccount)
    }
    suspend fun getNewFollowersCount():Long{
        return roomDao.getNewFollowersCount(prefs.selectedAccount)
    }
    suspend fun addMediaLikeUser(likeData : List<PostUserData>){
        roomDao.addMediaLikeUser(likeData)
    }
    suspend fun getLikeUserMedia(): List<PostUserData>{
        return roomDao.getLikeUserMedia(prefs.selectedAccount)
    }

    suspend fun updateFollowersNewProfilePicture(userId:Long,url:String){
        roomDao.updateFollowersNewProfilePicture(userId,url)
    }

    suspend fun updateFollowingNewProfilePicture(userId:Long,url:String){
        roomDao.updateFollowingNewProfilePicture(userId,url)
    }

    suspend fun updateOldFollowersNewProfilePicture(userId:Long,url:String){
        roomDao.updateOldFollowersNewProfilePicture(userId, url)
    }

    suspend fun updateOldFollowingNewProfilePicture(userId:Long,url:String){
        roomDao.updateOldFollowingNewProfilePicture(userId, url)
    }

}