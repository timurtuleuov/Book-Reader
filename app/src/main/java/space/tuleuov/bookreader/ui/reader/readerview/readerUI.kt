package space.tuleuov.bookreader.ui.reader.readerview

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json.Default.configuration
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.hyphe.Haaivin

import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun readerUI(book: FB2Book, navController: NavController, haaivin: Haaivin) {
    val barStates = rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    var columnHeightPx by remember {
        mutableStateOf(0)
    }
    val heightPage = 1427
//    val pageCountLimit by remember{
//        mutableStateOf(0)
//    }
    var pageCount by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(book) {
        val result = withContext(Dispatchers.IO) {
            countPageAsync(book)
        }

        pageCount = result
    }

    com.google.accompanist.insets.ui.Scaffold(
        topBar = {
            TopBar(navController = navController, title = book.title, barStates = barStates)
        },
        bottomBar = {
            BottomBar(barStates = barStates, pageCount)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = { toggleBars(barStates) },
                    indication = null,
                    interactionSource = interactionSource
                )

        ) {
            items(book.chapters.drop(1)) { chapter ->
                Text(
                    text = chapter.title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp,),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, top = 20.dp)

                )
                chapter.content.lines().forEach { line ->
                    val hyphLine = haaivin.hyphenate(string = line, dictionaryId = "ruhyph")
                    Text(
                        text = "      " + hyphLine,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            ,
                        textAlign = TextAlign.Justify,
                        style = TextStyle(
                            fontSize = 18.sp,
                            hyphens = Hyphens.Auto,
                            lineBreak = LineBreak.Paragraph,
                        )
                    )
                }
            }
        }
    }


}

private fun toggleBars(barStates: MutableState<Boolean>) {
    barStates.value = !barStates.value
}
@Composable
fun BottomBar(barStates: MutableState<Boolean>, pageCount: Int) {
    AnimatedVisibility(
        visible = barStates.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar(){
                var sliderPosition by remember { mutableFloatStateOf(0f) }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "1 из $pageCount", modifier = Modifier.align(Alignment.CenterHorizontally))
                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.Yellow,
                            inactiveTrackColor = Color.LightGray,
                        ),
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it }
                    )
                }
            }
        }
    )
}

@Composable
fun TopBar(navController: NavController, title: String, barStates: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = barStates.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                title = { title?.let { Text(text = it) } }
            )
        }
    )
}

suspend fun countPageAsync(book: FB2Book): Int {
    delay(1000)
    var pageCount = 0
    var columnHeightPx = 0
    book.chapters.forEach { chapter ->
        // Прибавляем высоту каждой строки текста к общей высоте
        columnHeightPx += 114
        chapter.content.lines().forEach {line ->
            val countLine = line.length / 40
            columnHeightPx += countLine * 38
        }
    }
    pageCount = columnHeightPx / 1427
    return pageCount
}
