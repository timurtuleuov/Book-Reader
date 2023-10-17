package space.tuleuov.bookreader.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.books.model.LocalBook

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
fun BookFound(book: LocalBook, navController: NavController) {
    Navigation(navController)
    BookInfo(book = book)
}

//Здесь должен быть navController. СЛЫШИШЬ ТИМУР?!
@Composable
fun Navigation(navController: NavController) {
    Row(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "назад")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Читать")
        }
    }
}

@Composable
fun BookInfo(book: LocalBook) {
    Box( modifier = Modifier.fillMaxSize().padding(start = 25.dp, top = 100.dp)) {

        Image(
            painter = painterResource(id = book.coverResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 280.dp, height = 300.dp)

                .clip(shape = RoundedCornerShape(15)),
            alignment = Alignment.CenterStart
        )
        Text(text = book.title)
        Text(text = book.author)
        Text(text = book.genre)
        Text(text = (book.yearOfPublication).toString())
        Text(text = (book.pageCount).toString())
    }
}