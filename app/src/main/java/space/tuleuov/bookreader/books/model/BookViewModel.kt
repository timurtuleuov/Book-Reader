package space.tuleuov.bookreader.books.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.tuleuov.bookreader.BookReaderApp
import space.tuleuov.bookreader.R
import space.tuleuov.bookreader.db.entity.Book

class BookViewModel(app: Application) : AndroidViewModel(app) {
    private val books: MutableLiveData<List<Book>> = MutableLiveData()
    private val db = (app as BookReaderApp).database
    init {
        // Инициализируйте список книг, например, загрузите его из базы данных или с сервера
        val bookList = db.bookDao().getAllBooks()
        books.value = bookList
    }

    fun getAllBooks(): List<Book>? {
        return books.value
    }
    fun getBookById(bookId: String): Book? {
        return books.value?.find { it.id == (bookId).toInt() }
    }
}
