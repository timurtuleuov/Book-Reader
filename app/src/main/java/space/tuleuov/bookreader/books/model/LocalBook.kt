package space.tuleuov.bookreader.books.model

data class LocalBook(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val pageCount: Int,
    val yearOfPublication: Int,
    val coverResId: Int
)
