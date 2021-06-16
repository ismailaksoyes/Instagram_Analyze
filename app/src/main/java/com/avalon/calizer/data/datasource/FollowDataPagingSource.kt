package com.avalon.calizer.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.MySharedPreferences
import javax.inject.Inject

class FollowDataPagingSource (private val roomDb: RoomRepository,private val repository: Repository,private val prefs:MySharedPreferences):PagingSource<Int, FollowData>() {


    override fun getRefreshKey(state: PagingState<Int, FollowData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowData> {
       val position = params.key ?: STARTING_INDEX
        Log.d("Position","  $position")
        val follow = roomDb.getFollowersData(position)
        Log.d("Position","Data->  $follow")

        val userCookie = roomDb.getAccountCookies(prefs.selectedAccount)
       follow.forEach { usersData->

           follow.map { userData->
               userData.profilePicUrl = ppUrl?.body()?.user?.profilePicUrl
               }

           }


       return LoadResult.Page(
            data = follow,
           prevKey = if (position == STARTING_INDEX) null else position -6,
           nextKey = if (follow.isNullOrEmpty()) null else position +6

        )
    }
companion object{const val STARTING_INDEX = 0}
    suspend fun getPpUrl(dsUserId:Long,userCookie:String):String?{
        val userGetData = repository.getUserDetails(userCookie,dsUserId)
        val ppUrl = if (userGetData.isSuccessful){
            userGetData.body()?.user?.profilePicUrl
        }else{
            null
        }
        return ppUrl
    }
}
