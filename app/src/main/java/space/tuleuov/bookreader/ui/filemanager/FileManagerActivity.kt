package space.tuleuov.bookreader.ui.filemanager

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import space.tuleuov.bookreader.R
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import space.tuleuov.bookreader.BookReaderApp
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.ui.authorization.UserPreferences
import space.tuleuov.bookreader.ui.reader.fb2reader.FB2Book
import space.tuleuov.bookreader.ui.reader.fb2reader.parseFB2
import space.tuleuov.bookreader.ui.reader.readerview.readerUI
import java.io.*
import java.net.URLEncoder
import java.time.LocalDate
import java.time.LocalDateTime

class FileManagerActivity : ComponentActivity() {
    private val storagePermissionCode = 101
    private val rootDirectory = Environment.getExternalStorageDirectory()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
fun FileManagerContent(directoryPath: String?, navController: NavController, app: Application) {
    val db = (app as BookReaderApp).database

    val userPreferences =  UserPreferences(app)
    val savedUser = userPreferences.getUser()

    val selectedFiles = remember { mutableStateListOf<File>() }
    var fileNames by remember { mutableStateOf(listOf<String>()) }
    val permissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val context  = LocalContext.current
    val directory = if (directoryPath != null) File(directoryPath) else null

    val fileList = directory?.listFiles()
    fileList?.let {
        fileNames = fileList.map { it.name }
    }
    val onFileSelected: (File) -> Unit = { file ->
        if (selectedFiles.contains(file)) {
            selectedFiles -= file
        } else {
            selectedFiles += file
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)

                    }
                },
                title = { Text("Файловый менеджер") }
            )
        }
    ) {
        if (directoryPath != null) {
            Text(text = directoryPath, modifier = Modifier.padding(horizontal = 20.dp))
        }
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (permissionState.status.isGranted) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(fileNames) { fileName ->
                        val file = File(directoryPath, fileName)

                        val isDirectory = file.isDirectory

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            if (!isDirectory) {
                                Checkbox(
                                    checked = selectedFiles.contains(file),
                                    onCheckedChange = {
                                        if (it) {
                                            selectedFiles.add(file)
                                        } else {
                                            selectedFiles.remove(file)
                                        }
                                    }
                                )
                            }
                            Icon(
                                painterResource(if (isDirectory) R.drawable.ic_directory else R.drawable.ic_file),
                                contentDescription = null
                            )
                            Text(
                                text = fileName,
                                modifier = Modifier.clickable {
                                    if (isDirectory) {
                                        val pathToDirectory = URLEncoder.encode(file.path, "UTF-8")
                                        navController.navigate("fileManager/$pathToDirectory")
                                    } else {
                                        // Логика отрытия файла

                                        val pathToDirectory = URLEncoder.encode(file.path, "UTF-8")
                                        if (file.extension == "fb2"){
                                            val existingBook = db.bookDao().getBookByFileLocation(pathToDirectory)
                                            if (existingBook == null) {
                                                val book = parseFB2(FileInputStream(file), context)
                                                if (book != null && savedUser != null) {
                                                    db.bookDao().insert(
                                                        Book(
                                                            fileLocation = pathToDirectory,
                                                            bookName = book.title,
                                                            userId = savedUser.uid,
                                                            lastOpenedTime = System.currentTimeMillis(),
                                                            author = book.authors.joinToString(),
                                                            annotation = book.annotation.joinToString(),
                                                            series = "",
                                                            cover = book.img,
                                                            genre = "",
                                                            bookmark = ""
                                                        )
                                                    )
                                                }
                                                navController.navigate("readFile/${pathToDirectory}")
                                            } else{
                                                navController.navigate("readFile/${pathToDirectory}")
                                            }
                                        } else {
                                            Toast.makeText(context, "Нельзя открыть этот файл", Toast.LENGTH_LONG).show()
                                        }

                                    }
                                }
                            )
                        }
                    }
                }


            } else {
                Column {
                    val textToShow = if (permissionState.status.shouldShowRationale) {
                        "Вы не разрешили доступ приложению для чтения файлов"
                    } else {

                        "Вы не предоставили доступ к файлам. " +
                                "У вас еще есть шанс"
                    }
                    Text(textToShow)
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Разрешить доступ")
                    }
                }
            }
        }
    }
}
