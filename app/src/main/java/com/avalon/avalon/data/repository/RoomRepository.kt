package com.avalon.avalon.data.repository

import com.avalon.avalon.data.local.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomRepository(private val roomDao: RoomDao) {



    suspend fun addCookie(roomData: RoomData){
      roomDao.addCookie(roomData)
    }

     suspend fun getCookies():RoomData{
        return roomDao.readAllData()
    }

    suspend fun getNotFollow():List<FollowersData>{
        return roomDao.getNotFollow()
    }

    suspend fun addFollowers(followersData:List<FollowersData>){
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.deleteData()
            roomDao.addFollowers(followersData)
        }

    }
    suspend fun addLastFollowers(followersData: List<LastFollowersData>){
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.deleteData1()
            roomDao.lastAddFollowers(followersData)
        }
    }
    suspend fun addFollowing(followingData: FollowingData){
        roomDao.addFollowing(followingData)
    }
    suspend fun getFollowers(): List<FollowersData> {
        return roomDao.getFollowers()
    }
    suspend fun getFollowing():List<FollowingData>{
        return roomDao.getFollowing()
    }

}