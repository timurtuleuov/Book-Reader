package space.tuleuov.bookreader.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.tuleuov.bookreader.ui.theme.TextTitle
import kotlin.math.roundToInt


@Composable
fun BooksListPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title()
        SwipeableSample()
    }
}

@Composable
fun Title() {
    Text(
        text = "В тренде",
        fontSize = 18.sp,
        color = TextTitle
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableSample() {

    val squareSize = 500.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(300.dp)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun BookItem(book: String) {
    Box(
        modifier = Modifier
            .size(120.dp, 160.dp)
            .background(Color.Gray)
    ) {
        Text(
            text = book,
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}