package com.avalon.calizer.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.avalon.calizer.data.local.story.StoryViewerData
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.NotFollowStoryViewsViewModel


@Composable
fun StoryListContent(viewModel:NotFollowStoryViewsViewModel) {
    val storyData: List<StoryViewerData> by viewModel.testObserve.observeAsState(listOf())
    Log.d("StateTest",storyData.toString())
   // val storyData = remember { list }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = storyData,
            itemContent = {
                StoryListItem(user = it)
            })

    }
}
