package space.tuleuov.bookreader.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import space.tuleuov.bookreader.R
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.ui.theme.SupportText

@Composable
fun BookDetail(bookId: String, viewModel: BookViewModel, navController: NavController){
    val book = viewModel.getBookById(bookId)
    if (book != null) {
        BookFound(book = book, navController)
    } else {
        print("Книга не найдена")
    }
}

@Composable
fun BookFound(book: Book?, navController: NavController) {
    BookInfo(book = book, navController)
}

//Здесь должен быть navController. СЛЫШИШЬ ТИМУР?!
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookInfo(book: Book?, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)

                    }
                },
                actions = {
                    Button(
                        onClick = { /* TODO: Логика открытия книги */ },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = "Читать", color = Color.Yellow, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp )
                    }
                },
                title = { Text(text = "О документе", color = Color.White)},
                contentColor = LocalContentColor.current
            )
        }
    ) {
        // Основное содержание экрана (содержание книги, например)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, top = 25.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().horizontalScroll(rememberScrollState())) {
                Image(
                    painter = painterResource(id = R.drawable.one_piece),
                    contentDescription = null,

                    modifier = Modifier
                        .size(width = 250.dp, height = 390.dp)
                        .clickable(onClick = { /* TODO: Логика открытия книги */ })
                        .clip(shape = RoundedCornerShape(1)),
                    alignment = Alignment.CenterStart
                )
                if (book != null) {
                    Text(text = book.bookName, fontSize = 30.sp)
                }
                //Здесь должен быть ряд из того что можно сделать с книгой

                //
                Box(
                    modifier = Modifier.fillMaxWidth().
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            book.author?.let { it1 -> Text(text = it1, fontSize = 20.sp) }
                        }
                        Text(text = "Автор", color = SupportText)}
                    }

                Box(
                    modifier = Modifier.
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    ).fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Серия", color = SupportText)
                    }
                    }
                Box(
                    modifier = Modifier.
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    ).fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            book.genre?.let { it1 -> Text(text = it1, fontSize = 20.sp) }
                        }
                        Text(text = "Жанр", color = SupportText)}
                    }

                Box(
                    modifier = Modifier.
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    ).fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Текущая закладка и время последнего чтения", color = SupportText)
                    }
                }
                Box(
                    modifier = Modifier.
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    ).fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            Text(text = (book.series).toString(), fontSize = 20.sp)
                        }
                        Text(text = "Серия", color = SupportText)
                    }
                }
                Box(
                    modifier = Modifier.
                    clickable(
                        onClick = { /* TODO: Изменение метаданных */ }
                    ).fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()){
                        Text(text = "Формат и размер файла", color = SupportText)
                    }
                }


                //Цитаты
            }

        }
    }
}