package space.tuleuov.bookreader.books.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.tuleuov.bookreader.R

class BookViewModel : ViewModel() {
    private val books: MutableLiveData<List<LocalBook>> = MutableLiveData()

    init {
        // Инициализируйте список книг, например, загрузите его из базы данных или с сервера
        val bookList = listOf(
            LocalBook(
                id = 1,
                title = "One Piece",
                author = "Author 1",
                genre = "Genre 1",
                pageCount = 200,
                yearOfPublication = 2020,
                coverResId = R.drawable.one_piece
            ),
            LocalBook(
                id = 2,
                title = "Game of Thrones",
                author = "Author 2",
                genre = "Genre 2",
                pageCount = 250,
                yearOfPublication = 2019,
                coverResId = R.drawable.clashofkings
            ),
            LocalBook(
                id = 3,
                title = "Вечера на хуторе юлиз Диканьки",
                author = "Николай Васильевич Гоголь",
                genre = "Мистика",
                pageCount = 300,
                yearOfPublication = 2021,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 4,
                title = "Garry Potter",
                author = "Author 4",
                genre = "Genre 4",
                pageCount = 220,
                yearOfPublication = 2018,
                coverResId = R.drawable.harry_potter
            ),
            LocalBook(
                id = 5,
                title = "How i've met your Mother",
                author = "Author 5",
                genre = "Genre 5",
                pageCount = 280,
                yearOfPublication = 2022,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 6,
                title = "По стечению сложных обстоятельств",
                author = "Author 6",
                genre = "Genre 6",
                pageCount = 240,
                yearOfPublication = 2017,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 7,
                title = "Ведьмак. Полный сборник",
                author = "Author 7",
                genre = "Genre 7",
                pageCount = 190,
                yearOfPublication = 2015,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 8,
                title = "Зов Ктулху",
                author = "Author 8",
                genre = "Genre 8",
                pageCount = 270,
                yearOfPublication = 2023,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 9,
                title = "Киберпанк",
                author = "Author 9",
                genre = "Genre 9",
                pageCount = 310,
                yearOfPublication = 2016,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 10,
                title = "Book 10",
                author = "Author 10",
                genre = "Genre 10",
                pageCount = 230,
                yearOfPublication = 2014,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 11,
                title = "Book 11",
                author = "Author 11",
                genre = "Genre 11",
                pageCount = 260,
                yearOfPublication = 2024,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 12,
                title = "Book 12",
                author = "Author 12",
                genre = "Genre 12",
                pageCount = 280,
                yearOfPublication = 2022,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 13,
                title = "Book 13",
                author = "Author 13",
                genre = "Genre 13",
                pageCount = 190,
                yearOfPublication = 2015,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 14,
                title = "Book 14",
                author = "Author 14",
                genre = "Genre 14",
                pageCount = 270,
                yearOfPublication = 2023,
                coverResId = R.drawable.i565152
            ),
            LocalBook(
                id = 15,
                title = "Book 15",
                author = "Author 15",
                genre = "Genre 15",
                pageCount = 310,
                yearOfPublication = 2016,
                coverResId = R.drawable.i565152
            )
        )
        books.value = bookList
    }

    fun getAllBooks(): List<LocalBook>?{
        return books.value
    }
    fun getBookById(bookId: String): LocalBook? {
        return books.value?.find { it.id == (bookId).toInt() }
    }
}
