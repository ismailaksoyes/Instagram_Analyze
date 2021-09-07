package com.avalon.calizer.data.local


import androidx.room.*
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.local.follow.OldFollowersData
import com.avalon.calizer.data.local.follow.OldFollowingData
import com.avalon.calizer.data.local.profile.AccountsInfoData

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(accountsData: AccountsData)

    @Query("UPDATE accounts_info SET followersType = :followersType, followingType =:followingType WHERE userId  = :userId")
    suspend fun updateUserType(userId:Long,followersType:Long,followingType:Long)

    @Query("SELECT * FROM accounts_table WHERE dsUserID= :userId")
    suspend fun getUserCookies(userId:Long):AccountsData

    @Query("SELECT * FROM accounts_table WHERE dsUserID = :userId")
    suspend fun getSaveFollowingType(userId: Long):AccountsData

    @Query("SELECT * FROM accounts_table WHERE dsUserID = :userId")
    suspend fun getSaveFollowersType(userId: Long):AccountsData

    @Query("UPDATE accounts_table SET profilePic = :profile_Pic, userName = :user_name WHERE dsUserID = :ds_userId")
    suspend fun updateAccountData(profile_Pic : String?, user_name:String?, ds_userId:String?)

    @get:Query("SELECT * FROM accounts_table")
    val getAllAccountDetails : List<AccountsData>

    @Query("SELECT * FROM accounts_info WHERE userId= :userId")
    suspend fun getUserInfo(userId:Long): AccountsInfoData

    @Query("SELECT * FROM followers_table WHERE analyzeUserId=:userId ORDER BY dsUserID ASC LIMIT 12 OFFSET :position")
    suspend fun getFollowersData(position:Int,userId: Long):List<FollowersData>

    @Query("SELECT * FROM following_table WHERE analyzeUserId=:userId ORDER BY dsUserID ASC LIMIT 12 OFFSET :position")
    suspend fun getFollowingData(position:Int,userId: Long):List<FollowingData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowersData(followData:List<FollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowingData(followData:List<FollowingData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOldFollowersData(followData:List<OldFollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOldFollowingData(followData:List<OldFollowingData>)

    @Query("DELETE FROM followers_table WHERE analyzeUserId=:userId")
    suspend fun deleteFollowersData(userId: Long)

    @Query("DELETE FROM following_table WHERE analyzeUserId=:userId")
    suspend fun deleteFollowingData(userId: Long)

    @Query("UPDATE accounts_table SET isFirstFollowersAnalyze=0 WHERE dsUserID=:userId")
    suspend fun updateFollowersSaveType(userId: Long)

    @Query("UPDATE accounts_table SET isFirstFollowingAnalyze=0 WHERE dsUserID=:userId")
    suspend fun updateFollowingSaveType(userId: Long)


    @Query("SELECT DISTINCT * FROM followers_table WHERE  analyzeUserId=:userId ORDER BY dsUserID ASC LIMIT 12 OFFSET :position")
    suspend fun getUnFollowers(userId: Long,position: Int):List<FollowersData>

    @Query("SELECT DISTINCT * FROM followers_table WHERE  analyzeUserId='19748713375' ORDER BY dsUserID ")
    suspend fun getNewFollowers():List<FollowersData>


/**
    @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM last_followers_table")
    suspend fun getUnFollowers(): List<FollowData>

    @Query("SELECT * FROM last_followers_table EXCEPT SELECT * FROM followers_table")
    suspend fun getEarnFollowers():List<FollowData>
    **/
}