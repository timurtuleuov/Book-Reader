package space.tuleuov.bookreader.ui.component

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import space.tuleuov.bookreader.R
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.ui.theme.SupportText
import space.tuleuov.bookreader.ui.theme.TextTitle
import java.io.IOException
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

val densityDpi = Resources.getSystem().displayMetrics.densityDpi



@Composable
fun BookItem(book: Book, navController: NavController) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(170.dp)
            .padding(start = 15.dp, end = 13.dp)
            .clickable {
                navController.navigate("bookDetails/${book.id}")
            }
    ) {
        val context  = LocalContext.current
        val contentResolver = context.contentResolver
        val bitmap: Bitmap? = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        contentResolver,
                        Uri.parse(book?.cover)
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(book?.cover))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        var imageBitmap by remember { mutableStateOf<Bitmap?>(bitmap) }
        Image(
            painter = rememberAsyncImagePainter(imageBitmap),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 140.dp, height = 143.dp)

                .clip(shape = RoundedCornerShape(15)),
            alignment = Alignment.CenterStart
        )
        Text(
            text = book.bookName,
            color = SupportText,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    Box(
        modifier = Modifier
            .size(width = 327.dp, height = 54.dp)
            .shadow(elevation = 2.dp, ambientColor = Color.Black, shape = RoundedCornerShape(45.dp))
            .background(Color.White, shape = RoundedCornerShape(45.dp))
    ) {
        TextField(
            value = state.value,
            onValueChange = { newValue ->
                state.value = newValue
            },
            placeholder = {
                Text(text = "Поиск книг", color = Color.Gray, fontSize = 18.sp)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
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

        Title(navController)

        val books = viewModel.getAllBooks()


        if (books != null) {
            BooksList(books = books, searchQuery = textState.value, navController)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }

}

@Composable
fun Title(navController: NavController) {
    val rootDirectory = Environment.getExternalStorageDirectory()
    val encodedPath = URLEncoder.encode(rootDirectory.path, "UTF-8")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Мои книги",
            fontSize = 18.sp,
            color = TextTitle,
            modifier = Modifier.padding(start=20.dp)
        )
//        Spacer(Modifier.weight(0.5f))

//        IconButton(onClick = { /*TODO*/ }, modifier = Modifier
//            .background(Color.Transparent)
//            .size(64.dp)) {
//            Icon(painter = painterResource(id = R.drawable.baseline_more_horiz_18), contentDescription = "")
//        }
        var expanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(end = 20.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_18), contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {

                DropdownMenuItem(onClick = { navController.navigate("fileManager/$encodedPath") }) {
                    Text("Поиск файлов", fontSize = 16.sp)
                }
                DropdownMenuItem(onClick = { /* TODO: Made filter of books*/ }) {
                    Text("Сортировать", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun BooksList(books: List<Book>, searchQuery: TextFieldValue, navController: NavController) {
    val filteredBooks = if (searchQuery.text.isEmpty()) {
        if (books.size > 6) {
            BookListIfMore6(books = books as ArrayList<Book>, navController)
        } else {
            if (books.size in 4..6) {
                BookListIfLess6(books = books as ArrayList<Book>, navController)
            } else {
                BookListIfLess3(books = books, navController)
            }
        }
    } else {
        books.filter { it.bookName.contains(searchQuery.text, ignoreCase = true) }

        val filteredBooks = ArrayList<Book>()
        for (book in books) {
            if (book.bookName.lowercase(Locale.getDefault())
                    .contains(searchQuery.text.lowercase(Locale.getDefault()))
            ) {
                filteredBooks.add(book)
            }
        }

        if (filteredBooks.size > 6) {
            BookListIfMore6(books = filteredBooks, navController)
        } else {
            if (filteredBooks.size in 4..6) {
                BookListIfLess6(books = filteredBooks, navController)
            } else {
                BookListIfLess3(books = filteredBooks, navController)
            }
        }
    }
}

//Если книг меньше или равно 3
@Composable
fun BookListIfLess3(books: List<Book>, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(335.dp)
        .horizontalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.forEach { book ->
                BookItem(book = book, navController)
            }
        }
    }
}

@Composable
fun BookListIfLess6(books: ArrayList<Book>, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(335.dp)
        .horizontalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.take(3).forEach { book ->
                BookItem(book = book, navController)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(horizontal = 0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.takeLast(books.size - 3).forEach { book ->
                BookItem(book = book, navController)
            }
        }
    }
}
@Composable
fun BookListIfMore6(books: ArrayList<Book>, navController: NavController) {
    val halfSize = (books.size + 1) / 2
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(335.dp)
        .horizontalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            books.slice(0 until halfSize).forEach { book ->
                BookItem(book = book, navController)
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier
                .height(165.dp)
                .align(Alignment.BottomStart)
                .padding(horizontal = 0.dp, vertical = 1.dp)
        ) {
            books.slice(halfSize until books.size).forEach { book ->
                BookItem(book = book, navController)
            }
        }

    }
}