package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avalon.calizer.data.datasource.FollowDataPagingSource
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.data.local.profile.AccountsInfoData
import com.avalon.calizer.data.repository.RoomRepository
import com.avalon.calizer.ui.main.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FollowViewModel @Inject constructor(private val dbRepository: RoomRepository):ViewModel() {

    val followers : Flow<PagingData<FollowData>> = Pager(PagingConfig(pageSize = 20)){
        FollowDataPagingSource(dbRepository)
    }.flow.cachedIn(viewModelScope)



}