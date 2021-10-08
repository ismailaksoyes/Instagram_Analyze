package com.avalon.calizer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.avalon.calizer.data.local.story.NoFollowStoryData
import com.avalon.calizer.utils.DataProvider

@Composable
fun StoryListContent() {
    val storyData = remember { DataProvider.noFollowStoryData }
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