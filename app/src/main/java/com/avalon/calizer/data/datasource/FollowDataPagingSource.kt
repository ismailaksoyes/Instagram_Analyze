package com.avalon.calizer.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.RoomDao
import com.avalon.calizer.data.repository.RoomRepository
import javax.inject.Inject

class FollowDataPagingSource(private val roomDb: RoomRepository):PagingSource<Int, FollowData>() {


    override fun getRefreshKey(state: PagingState<Int, FollowData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowData> {
       val position = params.key ?: STARTING_INDEX
        Log.d("Position","  $position")
        val follow = roomDb.getFollowersData()
       return LoadResult.Page(
            data = follow,
           prevKey = if (position == STARTING_INDEX) null else position -1,
           nextKey = if (follow.isNullOrEmpty()) null else position +1

        )
    }
companion object{const val STARTING_INDEX = 0}
}
