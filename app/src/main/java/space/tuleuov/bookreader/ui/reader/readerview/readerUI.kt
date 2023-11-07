package space.tuleuov.bookreader.ui.reader.readerview

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book
import space.tuleuov.bookreader.ui.reader.fb2reader.parseFB2
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

//@Composable
//fun detectType(file: InputStream, navController: NavController){
//
//
//    readerUI(parseFB2(file), navController)
//
//}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun readerUI(book: FB2Book, navController: NavController) {

    println("Название книги ${book.title}")

    println("ошибка здесь 2")
//    val rememberedBook = rememberSaveable { book }
    println("ошибка здесь 3")
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
        LazyColumn (modifier = Modifier){
            items(book.body.lines().map { it.toString() }) { line ->
                Text(text = line, modifier = Modifier.padding(15.dp))
            }
        }


    }
}