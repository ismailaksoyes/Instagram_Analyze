package com.avalon.avalon.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CookieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCookie(cookieData: CookieData)

    @Query("SELECT * FROM cookie_table WHERE cookieId=1")
    suspend fun readAllData(): CookieData

    @Insert
    suspend fun addFollowers(followersData: FollowersData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowing(followingData: FollowingData)

    @Query("SELECT * FROM followers_table")
    suspend fun getFollowers():List<FollowersData>


    @Query("SELECT * FROM following_table")
    suspend fun getFollowing():List<FollowingData>

   @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM following_table")
    suspend fun getNotFollow():List<FollowersData>
}