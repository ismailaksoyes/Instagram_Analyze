package com.avalon.calizer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.avalon.calizer.R
import com.avalon.calizer.data.local.story.NoFollowStoryData

@Composable
fun StoryListItem(user: NoFollowStoryData) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(2.dp))
    ) {
        Row (verticalAlignment = Alignment.CenterVertically
        ,modifier = Modifier.padding(15.dp)){
            UserImage()
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                user.username?.let { Text(text = it, style = MaterialTheme.typography.subtitle1) }
            }
        }
    }
}



@Composable
private fun UserImage() {
    Image(
        painter = rememberImagePainter(
            data = "https://cdn.business2community.com/wp-content/uploads/2014/04/profile-picture-300x300.jpg",
            builder = {
                transformations(CircleCropTransformation())
            }
        ),
        contentDescription = null,
        modifier = Modifier.size(50.dp)
    )
}