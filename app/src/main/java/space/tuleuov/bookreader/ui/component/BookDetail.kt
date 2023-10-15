package space.tuleuov.bookreader.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import space.tuleuov.bookreader.books.model.BookViewModel

@Composable
fun BookDetail(bookId: String, viewModel: BookViewModel){
    val book = viewModel.getBookById(bookId)
    if (book != null) {
        print("Книга называется ${book.title}")
    } else {
        print("Книга не найдена")
    }
    if (book != null) {
        Text(text = book.title)
    }
}