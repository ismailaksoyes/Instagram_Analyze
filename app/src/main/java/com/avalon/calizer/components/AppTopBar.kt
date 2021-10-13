package com.avalon.calizer.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.avalon.calizer.R
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.NotFollowStoryViewsViewModel
import kotlin.coroutines.coroutineContext


@Composable
fun AppTopBar(viewModel: NotFollowStoryViewsViewModel) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .height(50.dp)
        ,elevation = 2.dp
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Image(
                painterResource(id = R.drawable.ic_back_ico),
                "",
                modifier = Modifier.size(25.dp, 25.dp)
                    .clickable { viewModel.navigateStory() },
                alignment = Alignment.Center
            )
            Text(text = "Not Follow View", Modifier.fillMaxWidth(), textAlign = TextAlign.Center,style = MaterialTheme.typography.subtitle2)
        }



    }

}

