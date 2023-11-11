package space.tuleuov.bookreader.ui.reader.readerview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import space.tuleuov.bookreader.hyphe.Haaivin

import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun readerUI(book: FB2Book, navController: NavController, haaivin: Haaivin) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                title = { book.title?.let { Text(text = it) } }
            )
        }
    ) {
        LazyColumn() {
            items(book.chapters.drop(1)) { chapter ->
                Text(text = chapter.title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp,),
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 20.dp))
                chapter.content.lines().forEach { line ->
                    val hyphLine = haaivin.hyphenate(string = line, dictionaryId = "ruhyph")
                    Text(

                        text = "\t\t\t"+ hyphLine,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        textAlign = TextAlign.Justify,
                        style = TextStyle(
                            fontSize = 18.sp,
                            hyphens = Hyphens.Auto,
                            lineBreak = LineBreak.Paragraph,

                        ),

                    )

                }

            }
        }


    }
}
