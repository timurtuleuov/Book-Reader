package space.tuleuov.bookreader.ui.component

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import space.tuleuov.bookreader.BookReaderApp
import space.tuleuov.bookreader.books.model.BookViewModel
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.ui.theme.SupportText

@Composable
fun BookDetail(bookId: String, viewModel: BookViewModel, navController: NavController, pickMedia: ActivityResultLauncher<PickVisualMediaRequest>){
    val book = viewModel.getBookById(bookId)
    if (book != null) {
        BookFound(book = book, navController, pickMedia)
    } else {
        print("Книга не найдена")
    }
}
private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        if (cursor != null) {
            cursor.moveToFirst()
        }
        cursor?.getString(column_index!!)
    } catch (e: Exception) {
        Log.e(TAG, "getRealPathFromURI Exception : $e")
        ""
    } finally {
        cursor?.close()
    }
}
@Composable
fun BookFound(book: Book?, navController: NavController, pickMedia: ActivityResultLauncher<PickVisualMediaRequest>) {
    BookInfo(book = book, navController, pickMedia)
}
//Здесь должен быть navController. СЛЫШИШЬ ТИМУР?!
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookInfo(book: Book?, navController: NavController, pickMedia: ActivityResultLauncher<PickVisualMediaRequest>,  app: Application = (LocalContext.current.applicationContext as Application)) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)

                    }
                },
                actions = {
                    Button(
                        onClick = {
                            if (book != null) {
                                navController.navigate("readFile/${book.fileLocation}")
                            }
                        },
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
        val context  = LocalContext.current
        val db = (app as BookReaderApp).database
        var showDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember {mutableStateOf(false)}
        var dialogState by remember { mutableStateOf("") }
        val bookCoverPath = Uri.parse(book?.cover)

        var imageUri by remember {
            mutableStateOf<Uri?>(bookCoverPath)
        }
        val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri != null) {
                if (book != null) {
                    imageUri = uri
                    val imagePath = getRealPathFromURI(context, uri)
//                    val decodedImageUrl = java.net.URLDecoder.decode(uri.get, "UTF-8")

                    db.bookDao().update(book.copy(cover = imagePath))
                }
            }
        }



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, top = 25.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,

                    modifier = Modifier
                        .size(width = 250.dp, height = 390.dp)
                        .clickable(onClick = {
                            if (book != null) {
                                navController.navigate("readFile/${book.fileLocation}")
                            }
                        })
                        .clip(shape = RoundedCornerShape(1)),
                    alignment = Alignment.CenterStart
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                showDialog = true;
                                dialogState = "Название книги"
                            }
                        )
                        .fillMaxWidth()
                ) {if (showDialog) {
                    if (book != null) {
                        BookMetadataDialog(
                            book = book,
                            state = dialogState,
                            onDismiss = { showDialog = false }
                        )
                    }
                }
                        if (book != null) {
                            Text(text = book.bookName, fontSize = 25.sp, softWrap = true)
                        }
                }
                //Здесь должен быть ряд из того что можно сделать с книгой
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                // Действия при нажатии на кнопку "Добавить в избранное"
                            }
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = "Добавить в избранное")
                        }

                        IconButton(
                            onClick = {
                                // Действия при нажатии на кнопку "Хочу прочитать"
                            }
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Читаю")
                        }

                        IconButton(
                            onClick = {
                                // Действия при нажатии на кнопку "Добавить в прочитанное"
                            }
                        ) {
                            Icon(Icons.Default.Done, contentDescription = "Добавить в прочитанное")
                        }

                        IconButton(
                            onClick = {
                                val url: Uri
                                pickImage.launch("image/*")


                            },
                        ) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Изменить обложку")
                        }

                        IconButton(
                            onClick = {
                                // Действия при нажатии на кнопку "Поделиться файлом"
                            }
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Поделиться файлом")
                        }

                        IconButton(
                            onClick = {
                                showDeleteDialog = true
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                        }

                        if (showDeleteDialog) {
                            DeleteConfirmationDialog(
                                onConfirm = {
                                    // Действия при подтверждении удаления
                                    if (book != null) {
                                        db.bookDao().delete(book)
                                        navController.popBackStack()
                                        Toast.makeText(context, "Книга успешно удалена", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onDismiss = {
                                    showDeleteDialog = false
                                }
                            )
                        }
                    }
                }

                //
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                showDialog = true;
                                dialogState = "Автор"
                            }
                        )
                        .fillMaxWidth()
                ) {if (showDialog) {
                    if (book != null) {
                        BookMetadataDialog(
                            book = book,
                            state = dialogState,
                            onDismiss = { showDialog = false }
                        )
                    }
                }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            val result = book.author
                                ?.removeSurrounding("[", "]")
                                ?.split(", ")
                                ?.map { it.trim() }
                                ?.filter { it.isNotEmpty() }

                            book.author?.let { it1 ->
                                if (result != null) {
                                    val groupedResult = result.chunked(2)
                                    for (group in groupedResult.take(2)) {
                                        Text(text = group.joinToString(", "), fontSize = 20.sp)
                                    }
                                }
                            }
                        }
                        Text(text = "Автор", color = SupportText)}
                    }

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                showDialog = true;
                                dialogState = "Серия"
                            }
                        )
                        .fillMaxWidth()
                ) {if (showDialog) {
                    if (book != null) {
                        BookMetadataDialog(
                            book = book,
                            state = dialogState,
                            onDismiss = { showDialog = false }
                        )
                    }
                }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            book.series?.let { it1 ->


                                Text(text = it1, fontSize = 20.sp)


                            }
                        }
                        Text(text = "Серия", color = SupportText)
                    }
                    }
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                showDialog = true;
                                dialogState = "Жанр"
                            }
                        )
                        .fillMaxWidth()
                ) {if (showDialog) {
                    if (book != null) {
                        BookMetadataDialog(
                            book = book,
                            state = dialogState,
                            onDismiss = { showDialog = false }
                        )
                    }
                }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (book != null) {
                            val result = book.genre
                                ?.removeSurrounding("[", "]")
                                ?.split(", ")
                                ?.map { it.trim() }
                                ?.filter { it.isNotEmpty() }

                            book.genre?.let { it1 ->
                                if (it1 != null) {
                                    if (result != null) {
                                        Text(text = "${result.take(1).joinToString()}", fontSize = 20.sp)
                                    }
                                }
                            }
                        }
                        Text(text = "Жанр", color = SupportText)}
                    }

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { /* TODO: Изменение метаданных */ }
                        )
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Текущая закладка и время последнего чтения", color = SupportText)
                    }
                }

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { /* TODO: Изменение метаданных */ }
                        )
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()){
                        if (book != null) {
                            book.fileLocation?.let { it1 -> Text(text = it1, fontSize = 20.sp, softWrap = true) }
                        }
                        Text(text = "Местоположение файла", color = SupportText)
                    }
                }


                //Цитаты
            }

        }
    }
}

@Composable
fun BookMetadataDialog(book: Book, state: String, app: Application = (LocalContext.current.applicationContext as Application), onDismiss: () -> Unit) {
    val db = (app as BookReaderApp).database
    val changeState = remember {
        mutableStateOf("")
    }



    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Изменить значение ${state}") },
        text = {
            Column {
                TextField(
                    value = changeState.value,
                    onValueChange = { changeState.value = it },
                    label = { Text(state) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (state == "Автор"){
                        book.author = changeState.value
                    } else if (state == "Серия") {
                        book.series = changeState.value
                    } else if (state == "Жанр") {
                        book.genre = changeState.value
                    } else if (state == "Название книги") {
                        book.bookName = changeState.value
                    }

                    db.bookDao().update(book)

                    onDismiss()
                }
            ) {
                Text(text = "Сохранить")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Отмена")
            }
        }
    )
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Удаление") },
        text = { Text(text = "Вы уверены, что хотите удалить эту книгу?") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = "Удалить")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Отмена")
            }
        }
    )
}