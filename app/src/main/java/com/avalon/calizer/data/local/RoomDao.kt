package com.avalon.calizer.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomDao {

    //accounts data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(accountsData: AccountsData)

    @Query("UPDATE accounts_table SET profilePic = :profile_Pic, userName = :user_name WHERE dsUserID = :ds_userId")
    suspend fun updateAccountData(profile_Pic : String?, user_name:String?, ds_userId:String?)

    @get:Query("SELECT * FROM accounts_table")
    val getAllAccountDetails : List<AccountsData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCookie(roomData: RoomData)

    @Query("SELECT * FROM cookie_table ")
    suspend fun readAllData(): RoomData


    @Query("DELETE FROM last_followers_table")
    suspend fun deleteLastFollowers()

    @Query("DELETE FROM last_following_table")
    suspend fun deleteLastFollowing()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowers(followersData:List<FollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowing(followingData:List<FollowingData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lastAddFollowers(lastFollowersData:List<LastFollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun lastAddFollowing(lastFollowingData: List<LastFollowingData>)

    @Query("SELECT * FROM followers_table")
    suspend fun getFollowers(): List<FollowersData>


    @Query("SELECT * FROM following_table")
    suspend fun getFollowing(): List<FollowingData>

    @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM last_followers_table")
    suspend fun getUnFollowers(): List<FollowersData>

    @Query("SELECT * FROM last_followers_table EXCEPT SELECT * FROM followers_table")
    suspend fun getEarnFollowers():List<FollowersData>
}