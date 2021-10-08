package com.avalon.calizer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avalon.calizer.R


@Composable
fun AppTopBar() {
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
            )
            Text(text = "TopBarText", Modifier.fillMaxWidth(), textAlign = TextAlign.Center,style = MaterialTheme.typography.subtitle2)
        }



    }

}


@Preview(showBackground = true, uiMode = 1, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AppTopBar()
}