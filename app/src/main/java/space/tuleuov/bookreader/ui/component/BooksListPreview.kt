package space.tuleuov.bookreader.ui.component

import android.content.res.Resources
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import space.tuleuov.bookreader.R
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.books.model.LocalBook
import space.tuleuov.bookreader.ui.theme.SupportText
import space.tuleuov.bookreader.ui.theme.TextTitle
import java.util.*
import kotlin.collections.ArrayList

val densityDpi = Resources.getSystem().displayMetrics.densityDpi

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    Box(
        modifier = Modifier
            .size(width = 327.dp, height = 49.dp)
            .shadow(elevation = 2.dp, ambientColor = Color.Black, shape = RoundedCornerShape(45.dp))
            .background(Color.White, shape = RoundedCornerShape(45.dp))
    ) {
        TextField(
            value = state.value,
            onValueChange = { newValue ->
                state.value = newValue
            },
            placeholder = {
                Text(text = "Поиск книг", color = Color.Gray)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value.text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            state.value = TextFieldValue("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                leadingIconColor = Color.Gray,
                trailingIconColor = Color.Gray,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

@Composable
fun BooksListPreview(navController: NavController, viewModel: BookViewModel) {
    val textState = remember {mutableStateOf(TextFieldValue(""))}
    SearchView(textState)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Title()

        val books = viewModel.getAllBooks()


        if (books != null) {
            BooksList(books = books, searchQuery = textState.value, navController)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }

}

@Composable
fun Title() {
    Row(
        verticalAlignment = Alignment.CenterVertically, // Выравниваем элементы по вертикали
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "В тренде",
            fontSize = 18.sp,
            color = TextTitle,
            modifier = Modifier.padding(start=20.dp)
        )
        Spacer(Modifier.weight(0.8f))
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier
            .background(Color.Transparent)
            .size(64.dp)) {
            Icon(painter = painterResource(id = R.drawable.baseline_more_horiz_18), contentDescription = "")
        }
    }

}

@Composable
fun BooksList(books: List<LocalBook>, searchQuery: TextFieldValue, navController: NavController) {
    val filteredBooks = if (searchQuery.text.isEmpty()){
        books
    } else {
        books.filter {it.title.contains(searchQuery.text, ignoreCase = true) }

        val filteredBooks = ArrayList<LocalBook>()
        for (book in books) {
            if (book.title.lowercase(Locale.getDefault())
                    .contains(searchQuery.text.lowercase(Locale.getDefault()))
            ) {
                filteredBooks.add(book)
            }
        }

        val halfSize = (filteredBooks.size + 1) / 2 // Вычисляем половину размера списка
        Box(modifier = Modifier
            .fillMaxHeight()
            .horizontalScroll(rememberScrollState())){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 0.dp), // Убираем отступы
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                filteredBooks.take(halfSize).forEach { book ->
                    BookItem(book = book, navController)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()

                    .padding(horizontal = 0.dp, vertical = 170.dp), // Убираем отступы
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                filteredBooks.takeLast(halfSize).forEach { book ->
                    BookItem(book = book, navController)
                }
            }
        }
    }
    val halfSize = (books.size + 1) / 2 // Вычисляем половину размера списка
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(335.dp)
        .horizontalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 0.dp), // Убираем отступы
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.take(halfSize).forEach { book ->
                BookItem(book = book, navController)
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier
                .height(165.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 0.dp, vertical = 1.dp) // Убираем отступы

        ) {
            books.takeLast(halfSize).forEach { book ->
                BookItem(book = book, navController)
            }
        }
    }
}









@Composable
fun BookItem(book: LocalBook, navController: NavController) {
    val imageWidthPx = 199
    val imageheightPx = 257
    val bookBlockHeightPx = 297
    val imageWidthDp = imageWidthPx / (densityDpi / 160f)
    val imageheightDp = imageheightPx / (densityDpi / 160f)
    val bookBlockHeightDp = bookBlockHeightPx / (densityDpi / 160f)

    Column(
        modifier = Modifier
            .width(130.dp) // Ширина столбца (2 книги в ряду)
            .height(170.dp)
            .padding(start = 15.dp, end = 13.dp)
            .clickable {
                navController.navigate("bookDetails/${book.id}")
            }
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
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