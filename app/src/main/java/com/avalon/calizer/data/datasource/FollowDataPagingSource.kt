package com.avalon.calizer.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.utils.MySharedPreferences
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class FollowDataPagingSource(
    private val roomDb: RoomRepository,
    private val repository: Repository,
    private val prefs: MySharedPreferences
) : PagingSource<Int, FollowData>() {


    override fun getRefreshKey(state: PagingState<Int, FollowData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowData> {
        val position = params.key ?: STARTING_INDEX
        val follow = roomDb.getFollowersData(position)
        val userCookie = roomDb.getAccountCookies(prefs.selectedAccount)
        follow.forEach { usersData ->
            follow.map { userData ->
                val userGetData =
                    userData.dsUserID?.let {
                        repository.getUserDetails(
                            userCookie.allCookie,
                            it
                        )
                    }
                val ppUrl = userGetData?.body()?.user?.profilePicUrl

                userData.profilePicUrl = ppUrl


            }


        }
        return LoadResult.Page(
            data = follow,
            prevKey = if (position == STARTING_INDEX) null else position - 1,
            nextKey = if (follow.isNullOrEmpty()) null else position + 1

        )
    }

    companion object {
        const val STARTING_INDEX = 0
    }


}
