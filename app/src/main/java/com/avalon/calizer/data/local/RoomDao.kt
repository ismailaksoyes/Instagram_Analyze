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

    @Query("UPDATE followers_table SET profilePicUrl = :url WHERE dsUserID=:userId")
    suspend fun updateFollowersNewProfilePicture(userId:Long,url:String)

    @Query("UPDATE following_table SET profilePicUrl = :url WHERE dsUserID=:userId")
    suspend fun updateFollowingNewProfilePicture(userId:Long,url:String)

    @Query("UPDATE old_followers_table SET profilePicUrl = :url WHERE dsUserID=:userId")
    suspend fun updateOldFollowersNewProfilePicture(userId:Long,url:String)

    @Query("UPDATE old_following_table SET profilePicUrl = :url WHERE dsUserID=:userId")
    suspend fun updateOldFollowingNewProfilePicture(userId:Long,url:String)

    @Query("SELECT * FROM accounts_table WHERE dsUserID= :userId")
    suspend fun getUserCookies(userId: Long): AccountsData

    @Query("SELECT * FROM accounts_table WHERE dsUserID = :userId")
    suspend fun getSaveFollowingType(userId: Long): AccountsData

    @Query("SELECT * FROM accounts_table WHERE dsUserID = :userId")
    suspend fun getSaveFollowersType(userId: Long): AccountsData

    @Query("UPDATE accounts_table SET profilePic = :profile_Pic, userName = :user_name WHERE dsUserID = :ds_userId")
    suspend fun updateAccountData(profile_Pic: String?, user_name: String?, ds_userId: String?)

    @get:Query("SELECT * FROM accounts_table")
    val getAllAccountDetails: List<AccountsData>

    @Query("SELECT * FROM accounts_info WHERE userId= :userId")
    suspend fun getUserInfo(userId: Long): AccountsInfoData

    @Query("SELECT * FROM followers_table WHERE analyzeUserId=:userId ORDER BY dsUserID ASC")
    suspend fun getFollowersData(userId: Long): List<FollowersData>

    @Query("SELECT * FROM followers_table WHERE analyzeUserId=:userId ORDER BY dsUserID ASC")
    suspend fun getAllFollowers(userId: Long):List<FollowersData>

    @Query("SELECT * FROM following_table WHERE analyzeUserId=:userId ORDER BY dsUserID ASC")
    suspend fun getFollowingData(userId: Long): List<FollowingData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowersData(followData: List<FollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowingData(followData: List<FollowingData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOldFollowersData(followData: List<OldFollowersData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOldFollowingData(followData: List<OldFollowingData>)

    @Query("DELETE FROM followers_table WHERE analyzeUserId=:userId")
    suspend fun deleteFollowersData(userId: Long)

    @Query("DELETE FROM following_table WHERE analyzeUserId=:userId")
    suspend fun deleteFollowingData(userId: Long)

    @Query("UPDATE accounts_table SET isFirstFollowersAnalyze=0 WHERE dsUserID=:userId")
    suspend fun updateFollowersSaveType(userId: Long)

    @Query("UPDATE accounts_table SET isFirstFollowingAnalyze=0 WHERE dsUserID=:userId")
    suspend fun updateFollowingSaveType(userId: Long)


    @Query("SELECT * FROM old_followers_table WHERE analyzeUserId=:userId AND uniqueType IN(SELECT uniqueType FROM old_followers_table WHERE analyzeUserId=:userId EXCEPT SELECT uniqueType FROM followers_table WHERE analyzeUserId=:userId)  ORDER BY dsUserID ASC")
    suspend fun getUnFollowers(userId: Long): List<FollowersData>

    @Query("SELECT Count() FROM old_followers_table WHERE analyzeUserId=:userId AND uniqueType IN(SELECT uniqueType FROM old_followers_table WHERE analyzeUserId=:userId EXCEPT SELECT uniqueType FROM followers_table WHERE analyzeUserId=:userId)")
    suspend fun getUnFollowersCount(userId: Long): Long

    @Query("SELECT * FROM following_table WHERE  analyzeUserId=:userId AND uniqueType IN (SELECT uniqueType FROM following_table WHERE analyzeUserId=:userId EXCEPT SELECT uniqueType FROM followers_table WHERE analyzeUserId=:userId)   ORDER BY dsUserID ASC")
    suspend fun getNotFollowers(userId: Long): List<FollowersData>

    @Query("SELECT * FROM followers_table WHERE analyzeUserId=:userId AND uniqueType IN (SELECT uniqueType FROM followers_table WHERE analyzeUserId=:userId  EXCEPT SELECT uniqueType FROM old_followers_table WHERE analyzeUserId=:userId ) ORDER BY dsUserID ASC")
    suspend fun getNewFollowers(userId: Long): List<FollowersData>

    @Query("SELECT Count() FROM followers_table WHERE analyzeUserId=:userId AND uniqueType IN (SELECT uniqueType FROM followers_table WHERE analyzeUserId=:userId  EXCEPT SELECT uniqueType FROM old_followers_table WHERE analyzeUserId=:userId )")
    suspend fun getNewFollowersCount(userId: Long): Long

    @Query("SELECT Count() FROM old_followers_table WHERE analyzeUserId=:userId")
    suspend fun getOldFollowersCount(userId:Long):Long

    @Query("SELECT Count() FROM followers_table WHERE analyzeUserId=:userId")
    suspend fun getFollowersCount(userId: Long):Long

}
