package com.avalon.calizer.data.repository


import com.avalon.calizer.data.local.*
import com.avalon.calizer.data.local.profile.AccountsInfoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class RoomRepository @Inject constructor(private val roomDao: RoomDao) {


    suspend fun addAccountInfo(accountsInfoData: AccountsInfoData){
        roomDao.addAccountInfo(accountsInfoData)
    }

    suspend fun getUserInfo(userId:Long):AccountsInfoData{
       return roomDao.getUserInfo(userId)
    }

    suspend fun updateUserType(userId:Long,followersType:Long,followingType:Long){
        roomDao.updateUserType(userId,followersType,followingType)
    }

    suspend fun getAccountCookies(userId: Long):AccountsData{
        return roomDao.getUserCookies(userId)
    }


    suspend fun addAccount(accountsData: AccountsData) {
        roomDao.addAccount(accountsData)
    }

    fun getAccounts(): List<AccountsData> {
        return roomDao.getAllAccountDetails
    }
   suspend fun getFollowersData(position:Int):List<FollowData>{
        return roomDao.getFollowersData(position)
    }

    suspend fun updateAccount(profile_Pic : String?, user_name:String?, ds_userId:String?){
        roomDao.updateAccountData(profile_Pic,user_name,ds_userId)
    }



//    suspend fun getNotFollow(): List<FollowData> {
//        return roomDao.getUnFollowers()
//    }

    suspend fun addFollowData(followData: List<FollowData>,userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            //roomDao.deleteLastData(userId)
            roomDao.addFollowData(followData)
        }

    }
    /**
    suspend fun addLastFollowers(followersData: List<LastFollowersData>) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Response", followersData.size.toString())
            roomDao.deleteLastFollowers()
            roomDao.lastAddFollowers(followersData)
        }
    }

    suspend fun addFollowing(followingData: List<FollowingData>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.addFollowing(followingData)
        }

    }

    suspend fun addLastFollowing(followingData: List<LastFollowingData>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.deleteLastFollowing()
            roomDao.lastAddFollowing(followingData)
        }
    }

    suspend fun getFollowers(): List<FollowData> {
        return roomDao.getFollowers()
    }

    suspend fun getFollowing(): List<FollowingData> {
        return roomDao.getFollowing()
    }
    **/
}