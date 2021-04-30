package com.avalon.calizer.data.local

import androidx.room.*
import com.avalon.calizer.data.local.profile.AccountsInfoData

@Dao
interface RoomDao {

    //accounts data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(accountsData: AccountsData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccountInfo(accountsInfoData: AccountsInfoData)

    @Query("SELECT * FROM accounts_info WHERE userId= :userId")
    suspend fun getUserInfo(userId:Long):AccountsInfoData

    @Query("SELECT * FROM accounts_table WHERE dsUserID= :userId")
    suspend fun getUserCookies(userId:Long):AccountsData

    @Query("UPDATE accounts_table SET profilePic = :profile_Pic, userName = :user_name WHERE dsUserID = :ds_userId")
    suspend fun updateAccountData(profile_Pic : String?, user_name:String?, ds_userId:String?)

    @get:Query("SELECT * FROM accounts_table")
    val getAllAccountDetails : List<AccountsData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFollowData(followData:List<FollowData>)


/**
    @Query("SELECT * FROM followers_table EXCEPT SELECT * FROM last_followers_table")
    suspend fun getUnFollowers(): List<FollowData>

    @Query("SELECT * FROM last_followers_table EXCEPT SELECT * FROM followers_table")
    suspend fun getEarnFollowers():List<FollowData>
    **/
}