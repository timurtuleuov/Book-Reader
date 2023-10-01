package space.tuleuov.bookreader.ui.component

import android.content.res.Resources
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.tuleuov.bookreader.books.model.LocalBook
import space.tuleuov.bookreader.books.model.TestData
import space.tuleuov.bookreader.ui.theme.SupportText
import space.tuleuov.bookreader.ui.theme.TextTitle

val densityDpi = Resources.getSystem().displayMetrics.densityDpi

@Composable
fun BooksListPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Title()
        val testData = TestData()
        val books = testData.loadLocalBooks()

        BooksList(books = books)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun Title() {
    Text(
        text = "В тренде",
        fontSize = 18.sp,
        color = TextTitle,
        modifier = Modifier.padding(start=20.dp)
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

                .padding(horizontal = 0.dp, vertical = 170.dp), // Убираем отступы
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
    val imageWidthPx = 199
    val imageheightPx = 257
    val bookBlockHeightPx = 297
    val imageWidthDp = imageWidthPx / (densityDpi / 160f)
    val imageheightDp = imageheightPx / (densityDpi / 160f)
    val bookBlockHeightDp = bookBlockHeightPx / (densityDpi / 160f)

    Column(
        modifier = Modifier
            .width(130.dp) // Ширина столбца (2 книги в ряду)
            .height(165.dp)
            .padding(start = 15.dp, end = 13.dp)
    ) {

        Image(
            painter = painterResource(id = book.coverResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 140.dp, height = 143.dp)

                .clip(shape = RoundedCornerShape(15)),
            alignment = Alignment.CenterStart
        )
        Text(
            text = book.title,
            color = SupportText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start=18.dp)
        )
//        Text(text = book.author)
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