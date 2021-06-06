package com.avalon.calizer.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.repository.RoomRepository
import javax.inject.Inject

class FollowDataPagingSource @Inject constructor(private val repository: RoomRepository):PagingSource<Int, FollowData>() {
    override fun getRefreshKey(state: PagingState<Int, FollowData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowData> {
        TODO("Not yet implemented")
    }
}