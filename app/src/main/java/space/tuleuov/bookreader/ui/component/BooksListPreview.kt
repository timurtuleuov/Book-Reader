package space.tuleuov.bookreader.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.tuleuov.bookreader.books.model.LocalBook
import space.tuleuov.bookreader.books.model.TestData
import space.tuleuov.bookreader.ui.theme.TextTitle


@Composable
fun BooksListPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title()
        val testData = TestData()
        val books = testData.loadLocalBooks()

        BooksList(books = books)
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

@Composable
fun BooksList(books: List<LocalBook>) {
    val halfSize = (books.size + 1) / 2 // Вычисляем половину размера списка
    Box(modifier = Modifier
        .fillMaxHeight()
        .horizontalScroll(rememberScrollState())){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 0.dp), // Убираем отступы
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.take(halfSize).forEach { book ->
                BookItem(book = book)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 200.dp), // Убираем отступы
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.takeLast(halfSize).forEach { book ->
                BookItem(book = book)
            }
        }
    }
}









@Composable
fun BookItem(book: LocalBook) {
    Column(
        modifier = Modifier
            .width(160.dp) // Ширина столбца (2 книги в ряду)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = book.coverResId),
            contentDescription = null, // Описание изображения
            modifier = Modifier
                .size(120.dp) // Размер обложки книги
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.title,
            style = TextStyle(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = book.author)
    }
}

//@Composable
//fun BookItem(book: String) {
//    Box(
//        modifier = Modifier
//            .size(120.dp, 160.dp)
//            .background(Color.Gray)
//    ) {
//        Text(
//            text = book,
//            color = Color.White,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//        )
//    }
//}