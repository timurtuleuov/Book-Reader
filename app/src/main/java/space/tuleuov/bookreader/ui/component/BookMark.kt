package space.tuleuov.bookreader.ui.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


private const val playIcon = 1
private const val loadingBar = 2
private const val pauseIcon = 3

private fun playTheBook(context: Context) {
    // Here play the song
    Log.i("SemicolonSpace", "playTheSong()")
    Toast.makeText(context, "Playing....", Toast.LENGTH_SHORT).show()
}


@Composable
private fun SetButtonIcon1(
    icon: ImageVector,
    iconDescription: String
) {
    Icon(
        modifier = Modifier
            .fillMaxSize(),
        imageVector = icon,
        contentDescription = iconDescription,
        tint = Color.White
    )
}

@Composable
fun BookMark(
    name: String,
    page: Int,
    cover: Int,
    isVisible: Boolean,
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                , // Добавляем кликабельность
            contentAlignment = Alignment.TopStart
        ) {
            // Весь ваш код BookMark остается без изменений
            Image(
                painter = painterResource(id = cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 57.dp, height = 49.dp)
                    .clip(shape = RoundedCornerShape(15)),
                alignment = Alignment.CenterStart
            )
            Column() {
                Text(text = name, fontSize = 32.sp)
                Text(text = "Страница $page", fontSize = 20.sp)
            }

        }
    }
}




