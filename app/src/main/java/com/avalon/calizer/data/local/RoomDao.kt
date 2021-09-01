package com.avalon.calizer.data.local


import androidx.room.*
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

    @Query("SELECT * FROM follow_table WHERE type=1 ORDER BY dsUserID ASC LIMIT 12 OFFSET :position")
    suspend fun getFollowersData(position:Int):List<FollowData>

    @Query("DELETE FROM remotekeys")
    suspend fun clearRemoteKeys()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowData(followData:List<FollowData>)

    @Query("DELETE FROM follow_table WHERE (type=1 OR type=3) AND analyzeUserId=:userId")
    suspend fun deleteLastData(userId: Long)

    @Query("SELECT DISTINCT * FROM follow_table WHERE type=3 AND analyzeUserId=:userId ORDER BY dsUserID ASC LIMIT 12 OFFSET :position")
    suspend fun getUnFollowers(userId: Long,position: Int):List<FollowData>

    @Query("SELECT DISTINCT * FROM follow_table WHERE type=0 AND type=1 AND analyzeUserId='19748713375' ORDER BY dsUserID ")
    suspend fun getNewFollowers():List<FollowData>


/**
    @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM last_followers_table")
    suspend fun getUnFollowers(): List<FollowData>

    @Query("SELECT * FROM last_followers_table EXCEPT SELECT * FROM followers_table")
    suspend fun getEarnFollowers():List<FollowData>
    **/
}